(ns aoc2019.23-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.23 :refer [solve1 solve2]]))

(def program (->> "2019/23" io/resource slurp))

(deftest test1
  (is (= (solve1 program) 20764)))

(deftest test2
  (is (= (solve2 program) 14805)))
