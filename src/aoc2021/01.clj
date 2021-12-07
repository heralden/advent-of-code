(ns aoc2021.01
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(defn parse [depths-string]
  (map edn/read-string (str/split-lines depths-string)))

(defn solve1 [depths]
  (->> depths
       (partition 2 1)
       (filter (partial apply <))
       (count)))

(defn solve2 [depths]
  (->> depths
       (partition 3 1)
       (map (partial apply +))
       (solve1)))
