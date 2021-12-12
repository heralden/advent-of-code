(ns aoc2021.11-test
  (:require [aoc2021.11 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526")

(def data (->> "2021/11" io/resource slurp))

(deftest test1
  (is (= 1656 (solve1 example)))
  (is (= 1637 (solve1 data))))

(deftest test2
  (is (= 195 (solve2 example)))
  (is (= 242 (solve2 data))))
