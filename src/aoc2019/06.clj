(ns aoc2019.06
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(defn read-orbits [data]
  (->> (string/split-lines data)
       (map #(string/split % #"\)"))
       (map #(mapv keyword %))))

(defn orbit-hierarchy [orbits]
  (reduce (fn [h [parent child]]
            (derive h child parent))
          (make-hierarchy)
          orbits))

(defn solve1 [data]
  (let [orbits    (read-orbits data)
        hierarchy (orbit-hierarchy orbits)]
    (->> (descendants hierarchy :COM)
         (map #(count (ancestors hierarchy %)))
         (reduce +))))

(defn solve2 [data]
  (let [orbits    (read-orbits data)
        hierarchy (orbit-hierarchy orbits)
        yous      (ancestors hierarchy :YOU)
        santas    (ancestors hierarchy :SAN)]
    (+ (count (set/difference yous santas))
       (count (set/difference santas yous)))))
