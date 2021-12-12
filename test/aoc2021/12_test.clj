(ns aoc2021.12-test
  (:require [aoc2021.12 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "start-A
start-b
A-c
A-b
b-d
A-end
b-end")

(def data (->> "2021/12" io/resource slurp))

(deftest test1
  (is (= 10 (solve1 example)))
  (is (= 3485 (solve1 data))))

(deftest test2
  (is (= 36 (solve2 example)))
  (is (= 85062 (solve2 data))))
