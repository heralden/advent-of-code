(ns aoc2021.05-test
  (:require [aoc2021.05 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2")

(def data (->> "2021/05" io/resource slurp))

(deftest test1
  (is (= 5 (solve1 example)))
  (is (= 6007 (solve1 data))))

(deftest test2
  (is (= 12 (solve2 example)))
  (is (= 19349 (solve2 data))))
