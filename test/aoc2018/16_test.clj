(ns aoc2018.16-test
  (:require [aoc2018.16 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-samples (->> "16-1" io/resource io/reader line-seq))

(def raw-program (->> "16-2" io/resource io/reader line-seq))

(deftest test1
  (is (= 624 (solve1 raw-samples))))

(deftest test2
  (is (= 584 (solve2 raw-samples raw-program))))
