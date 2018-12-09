(ns aoc2018.08-test
  (:require [aoc2018.08 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def raw-data (->> "08" io/resource io/reader line-seq first))

(def test-data "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")

(deftest test1
  (is (= 138 (solve1 test-data)))
  (is (= 46578 (solve1 raw-data))))

(deftest test2
  (is (= 66 (solve2 test-data))))
