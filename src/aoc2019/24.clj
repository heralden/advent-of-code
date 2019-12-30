(ns aoc2019.24
  (:require [clojure.string :as string]
            [clojure.math.numeric-tower :refer [expt]]))

(def ^:constant width 5)
(def ^:constant height 5)

(defn read-grid [data]
  (vec (remove #{"\n"} (string/split data #""))))

(defn neighbors [grid i]
  (map #(get grid % ".")
       (let [left  (dec i)
             right (inc i)
             up    (- i width)
             down  (+ i width)]
         (filter some? [(when (not= 0 (mod i width)) left)
                        (when (not= (dec width) (mod i width)) right)
                        (when (not= 0 (int (/ i height))) up)
                        (when (not= (dec height) (int (/ i height))) down)]))))

(defn step-cell [grid i cell]
  (let [living (count (filter #{"#"} (neighbors grid i)))]
    (cond
      (and (= cell "#") (not= living 1)) "."
      (and (= cell ".") (or (= living 1) (= living 2))) "#"
      :else cell)))

(defn step [grid]
  (vec (map-indexed (partial step-cell grid) grid)))

(defn biodiversity [grid]
  (->> (map-indexed vector grid)
       (filter (comp #{"#"} second))
       (map (comp #(expt 2 %) first))
       (reduce +)))

(defn solve1 [data]
  (let [grid (read-grid data)]
    (reduce
      (fn [[seen g] _]
        (let [g' (step g)]
          (if (contains? seen g')
            (reduced (biodiversity g'))
            [(conj seen g') g'])))
      [#{} grid]
      (range))))

;; Using the present grid vector wasn't viable for the recursive grids in part 2,
;; so I decided to reimplement the grid as a collection of living bug coordinates.

(defn read-grid-coord [data]
  (for [y (range height)
        x (range width)
        :when (= \# (get-in (string/split-lines data) [y x]))]
    [x y]))

(defn neighbors-rec [[gi x y]]
  (let [left (cond
               (= [x y] [3 2]) (mapv #(vector (inc gi) (dec width) %) (range height))
               (zero? x) [[(dec gi) 1 2]]
               :else [[gi (dec x) y]])
        right (cond
                (= [x y] [1 2]) (mapv #(vector (inc gi) 0 %) (range height))
                (= x (dec width)) [[(dec gi) 3 2]]
                :else [[gi (inc x) y]])
        up (cond
             (= [x y] [2 3]) (mapv #(vector (inc gi) % (dec height)) (range width))
             (zero? y) [[(dec gi) 2 1]]
             :else [[gi x (dec y)]])
        down (cond
               (= [x y] [2 1]) (mapv #(vector (inc gi) % 0) (range width))
               (= y (dec height)) [[(dec gi) 2 3]]
               :else [[gi x (inc y)]])]
    (apply concat [left right up down])))

(defn bugs-living [living]
  (let [living? (set living)]
    (filter (fn [bug]
              (= 1 (count (filter #(contains? living? %)
                                  (neighbors-rec bug)))))
            living)))

(defn lifeless-neighbors [living]
  (let [living? (set living)]
    (remove #(contains? living? %)
            (apply concat (map neighbors-rec living)))))

(defn bugs-born [living]
  (->> (frequencies (lifeless-neighbors living))
       (filter (fn [[_k v]] (or (= v 1) (= v 2))))
       (map (fn [[k _v]] k))))

(defn generation [living _]
  (into (bugs-living living) (bugs-born living)))

(defn solve2 [minutes data]
  (let [living (map #(into [0] %) (read-grid-coord data))]
    (count (reduce generation living (range minutes)))))
