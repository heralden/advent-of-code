(ns aoc2019.12
  (:require [clojure.math.numeric-tower :refer [lcm abs]]))

(defn read-moons [data]
  (->> (re-seq #"-?\d+" data)
       (map #(Integer/parseInt %))
       (partition 3)
       (map vec)
       (into [])))

(defn axis [v1 a1 a2]
  (cond
    (= a1 a2) v1
    (> a1 a2) (dec v1)
    (< a1 a2) (inc v1)))

(defn velocity [m1 v1 m2]
  (mapv axis v1 m1 m2))

(defn remvec [v i]
  (vec (concat (subvec v 0 i)
               (subvec v (inc i)))))

(defn apply-velocity [moons i vel]
  (reduce (partial velocity (get moons i)) vel (remvec moons i)))

(defn gravity [v1 m1]
  (map + v1 m1))

(defn simulate-step [[moons vels] _]
  (let [vels' (vec (map-indexed (partial apply-velocity moons) vels))
        moons' (vec (map gravity vels' moons))]
    [moons' vels']))

(defn simulate [data steps]
  (let [moons (read-moons data)
        vels (vec (repeat (count moons) (vec (repeat 3 0))))]
    (reduce simulate-step [moons vels] (range steps))))

(defn energy [[moons vels]]
  (reduce +
          (map *
               (map #(transduce (map abs) + 0 %) moons)
               (map #(transduce (map abs) + 0 %) vels))))

(defn solve1 [steps data]
  (->> (simulate data steps)
       (energy)))

(defn zero-axis [vels axes]
  (filter (fn [ax] (every? zero? (map #(nth % ax) vels))) axes))

(defn simulate-cycles [[moons vels axes res] step]
  (let [vels' (vec (map-indexed (partial apply-velocity moons) vels))
        moons' (vec (map gravity vels' moons))]
    (if-let [ax (zero-axis vels' axes)]
      (let [res' (reduce #(assoc %1 %2 step) res ax)
            axes' (reduce #(disj %1 %2) axes ax)]
        (if (empty? axes')
          (reduced (map inc res'))
          [moons' vels' axes' res']))
      [moons' vels' axes res])))

(defn solve2 [data]
  (let [moons (read-moons data)
        vels (vec (repeat (count moons) (vec (repeat 3 0))))
        [a b c] (reduce simulate-cycles [moons vels #{0 1 2} [0 0 0]] (range))]
    (* 2 (lcm (lcm a b) c))))
