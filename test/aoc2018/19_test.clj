(ns aoc2018.19-test
  (:require [aoc2018.19 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-input (->> "2018/19" io/resource io/reader line-seq))

(def test-input ["#ip 0"
                 "seti 5 0 1"
                 "seti 6 0 2"
                 "addi 0 1 0"
                 "addr 1 2 3"
                 "setr 1 0 0"
                 "seti 8 0 4"
                 "seti 9 0 5"])

(deftest test1
  (is (= 7 (solve1 test-input)))
  (is (= 2047 (solve1 raw-input))))
