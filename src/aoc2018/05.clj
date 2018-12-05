(ns aoc2018.05
  (:require [clojure.string :as string]))

(def capital-letters (map char (range 65 91)))
(def lowercase-letters (map char (range 97 123)))

(def unit-pairs (concat (map str capital-letters lowercase-letters)
                        (map str lowercase-letters capital-letters)))

(defn remove-match [s match]
  (string/replace s (re-pattern match) ""))

(defn solve1 [polymer]
  (count (loop [prev-poly "", poly polymer]
           (if (= prev-poly poly)
             poly
             (recur poly (reduce remove-match
                                 poly
                                 unit-pairs))))))

(def unit-regexes (map #(str "[" %1 %2 "]")
                       lowercase-letters
                       capital-letters))

(defn solve2 [polymer]
  (->> (pmap #(solve1 (remove-match polymer %)) unit-regexes)
       (reduce min)))
