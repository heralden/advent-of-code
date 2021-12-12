(ns aoc2021.09-test
  (:require [aoc2021.09 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "2199943210
3987894921
9856789892
8767896789
9899965678")

(def data (->> "2021/09" io/resource slurp))

(deftest test1
  (is (= 15 (solve1 example)))
  (is (= 633 (solve1 data))))

(deftest test2
  (is (= 1134 (solve2 example)))
  (is (= 1050192 (solve2 data))))
