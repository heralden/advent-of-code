(ns aoc2021.02-test
  (:require [aoc2021.02 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "forward 5
down 5
forward 8
up 3
down 8
forward 2")

(def data (->> "2021/02" io/resource slurp))

(deftest test1
  (is (= 150 (solve1 example)))
  (is (= 1813801 (solve1 data))))

(deftest test2
  (is (= 900 (solve2 example)))
  (is (= 1960569556 (solve2 data))))
