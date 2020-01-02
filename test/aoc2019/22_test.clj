(ns aoc2019.22-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.22 :refer [solve1 solve2]]))

(def data (->> "2019/22" io/resource slurp))

(deftest test1
  (is (= [9 2 5 8 1 4 7 0 3 6]
         (solve1 10 "deal into new stack
cut -2
deal with increment 7
cut 8
cut -4
deal with increment 7
cut 3
deal with increment 9
deal with increment 3
cut -1")))
  (is (= 3143 (solve1 10007 data :grab 2019))))

#_(deftest test2
    (is (= -1 (solve2 119315717514047 101741582076661 data :grab-n 2020))))
