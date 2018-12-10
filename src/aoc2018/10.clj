(ns aoc2018.clj
  (:require [clojure.pprint :refer [pprint]
             clojure.java.io :as io]))

(defn read-point [point]
  (let [[px py vx vy]
        (->> (re-matches
               #"position=<(.\d+), (.\d+)> velocity=<(.\d+), (.\d+)>"
               point)
             rest
             (map read-string))]
    {:position [px py]
     :velocity [vx vy]}))

(defn limit-pair [points]
  (let [get-limit (fn [f-limit f-index]
                    (apply f-limit (map (comp f-index :position) points)))
        min-x (get-limit min first)
        max-x (get-limit max first)
        min-y (get-limit min second)
        max-y (get-limit max second)]
    [[min-x min-y] [max-x max-y]]))

(defn max-distance [points]
  (let [[[min-x min-y] [max-x max-y]] (limit-pair points)]
    (+ (Math/abs (- min-x max-x))
       (Math/abs (- min-y max-y)))))

(defn points-within? [distance points]
  (<= (max-distance points) distance))

(defn tick-points
  ([points] (tick-points 1 points))
  ([ticks points]
   (map (fn [{[px py] :position [vx vy] :velocity}]
          {:position [(+ px (* ticks vx))
                      (+ py (* ticks vy))]
           :velocity [vx vy]})
        points)))

(defn xy->i [area [x y]]
  (let [width (-> area meta :width)]
    (+ x (* y width))))

(defn write-points [area]
  (let [width (-> area meta :width)
        len (count area)]
    (with-open [w (io/writer "out.txt")]
      (dotimes [i (/ len width)]
        (.write w (str (subvec area
                               (* i width)
                               (* (inc i) width))
                       "\n"))))))

(defn print-points [points]
  (let [[min-xy max-xy] (limit-pair points)
        [gx gy] (mapv - max-xy min-xy)
        area (with-meta (vec (repeat (* gx (inc gy)) \.))
                        {:width gx})]
    (->> (reduce (fn [area {xy :position}]
                   (let [pos (mapv - xy min-xy)
                         index (xy->i area pos)]
                     (assoc area index \#)))
                 area
                 points)
         write-points)))

(defn solve [within ticks raw-points] ; 80 1 raw-data
  (let [points (map read-point raw-points)
        initial-distance (max-distance points)]
    (loop [points points, total-ticks 0]
      (assert (< (- (max-distance points)
                    initial-distance)
                 1000)
              "Points are diverging!")
      (if (points-within? within points)
        (do
          (print-points points)
          total-ticks)
        (recur (tick-points ticks points) (+ total-ticks ticks))))))
