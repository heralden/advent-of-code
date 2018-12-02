(ns aoc2018.01
  (:require [clojure.string :as string]))

(defn solve1 [data]
  (->> (string/split data #"\n")
       (map read-string)
       (reduce +)))

(defn solve2 [data]
  (->> (string/split data #"\n")
       (map read-string)
       cycle
       (reductions + 0)
       (reduce (fn [xs x]
                 (if (contains? xs x)
                   (reduced x)
                   (conj xs x)))
               #{})))
