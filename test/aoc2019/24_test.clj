(ns aoc2019.24-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.24 :refer [solve1 solve2]]))

(def data (-> "2019/24" clojure.java.io/resource slurp))

(deftest test1
  (is (= 2129920 (solve1 "....#
#..#.
#..##
..#..
#....")))
  (is (= 3186366 (solve1 data))))

(deftest test2
  (is (= 99 (solve2 10 "....#
#..#.
#.?##
..#..
#....")))
  (is (= 2031 (solve2 200 data))))
