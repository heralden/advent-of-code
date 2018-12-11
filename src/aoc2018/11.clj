(ns aoc2018.11
  (:require [clojure.string :refer [join]]))

(defn hundredth-digit [number]
  (-> number (mod 1000) (/ 100) int))

(defn power-level [serial [x y]]
  (let [rack-id (+ x 10)]
    (-> (* rack-id y)
        (+ serial)
        (* rack-id)
        hundredth-digit
        (- 5))))

(defn init-grid [serial length]
  (->> (for [y (range 0 length), x (range 0 length)]
            (power-level serial [x y]))
       (partition length)
       (mapv vec)))

(defn total-power [grid size [x y]]
  (let [powers (for [y (range y (+ y size)), x (range x (+ x size))]
                 (get-in grid [y x]))]
    (reduce + powers)))

(defn greatest-power [grid length size]
  (let [coords (for [y (range 0 (- length
                                   (dec size)))
                     x (range 0 (- length
                                   (dec size)))]
                 [x y])]
    (->> (map (juxt identity (partial total-power grid size)) coords)
         (apply max-key second))))

(defn solve1 [serial length]
  (let [grid (init-grid serial length)]
    (->> (greatest-power grid length 3)
         first
         (join ","))))

(defn solve2 [serial length max-size]
  (let [grid (init-grid serial length)
        [size [[x y] _]] (->> (range 0 max-size)
                              (pmap (juxt identity
                                          (partial greatest-power grid length)))
                              (apply max-key (comp second second)))]
    (join "," [x y size])))
