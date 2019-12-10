(ns aoc2019.10
  (:require [clojure.string :as string]))

(defn angle [[x1 y1] [x2 y2]]
  (Math/atan2 (- y2 y1) (- x2 x1)))

(defn map->asteroids [data]
  (let [m (mapv vec (string/split-lines data))]
    (for [y (range (count m))
          x (range (count (first m)))
          :when (= (get-in m [y x]) \#)]
      [x y])))

(defn count-sights [as a]
  (->> (map (partial angle a) as)
       (set)
       (count)))

(defn solve1 [data]
  (let [asteroids (map->asteroids data)]
    (transduce (map (partial count-sights asteroids)) max 0 asteroids)))
    
(defn atan2->degrees [x]
  (mod (+ (* x (/ 180 Math/PI)) 90) 360))

(defn solve2 [data]
  (let [asteroids (map->asteroids data)
        station   (apply max-key (partial count-sights asteroids) asteroids)
        order-map (->> (remove #{station} asteroids)
                       (group-by (comp atan2->degrees (partial angle station)))
                       (into (sorted-map-by <)))
        max-rots  (transduce (map count) max 0 (vals order-map))
        order     (reduce (fn [order rot]
                            (into order
                                  (comp (map #(get % rot)) (filter some?))
                                  (vals order-map)))
                          [] (range max-rots))
        [x y] (nth order 199)]
    (+ (* x 100) y)))
