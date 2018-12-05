(ns aoc2018.05
  (:require [clojure.string :refer [upper-case join replace reverse]]))

(defn char->unit [c]
  (let [unitv (-> (repeat 2 c) vec (update 1 upper-case))]
    (join "" unitv)))

(def alphabet (map char (range 97 123)))

(def all-units (concat (map char->unit alphabet)
                       (map clojure.string/reverse
                            (map char->unit alphabet))))

(defn remove-match [s match]
  (replace s match ""))

(defn solve1 [polymer]
  (count (loop [prev-poly "", poly polymer]
           (if (= prev-poly poly)
             poly
             (recur poly (reduce remove-match
                                 poly
                                 all-units))))))
