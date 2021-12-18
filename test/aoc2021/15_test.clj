(ns aoc2021.15-test
  (:require [aoc2021.15 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581")

(def data (->> "2021/15" io/resource slurp))

(deftest test1
  (is (= 40 (solve1 example)))
  (is (= 386 (solve1 data))))

(deftest test2
  (is (= 315 (solve2 example)))
  (is (= 2806 (solve2 data))))
