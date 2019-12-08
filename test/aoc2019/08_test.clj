(ns aoc2019.08-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.08 :refer [solve1 solve2]]))

(def data (->> "2019/08" io/resource slurp))

(deftest test1
  (is (= (solve1 data 25 6) 1806)))

(deftest test2
  (is (= (solve2 data 25 6)
         "((0 0 1 1 0 0 1 1 0 0 1 1 1 1 0 1 1 1 0 0 0 1 1 0 0)\n (0 0 0 1 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0)\n (0 0 0 1 0 1 0 0 1 0 1 1 1 0 0 1 0 0 1 0 1 0 0 1 0)\n (0 0 0 1 0 1 1 1 1 0 1 0 0 0 0 1 1 1 0 0 1 1 1 1 0)\n (1 0 0 1 0 1 0 0 1 0 1 0 0 0 0 1 0 1 0 0 1 0 0 1 0)\n (0 1 1 0 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0))\n")))
