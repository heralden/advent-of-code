(ns aoc2018.23-test
  (:require [aoc2018.23 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-input (->> "2018/23" io/resource io/reader line-seq))

(def test-input
  ["pos=<0,0,0>, r=4"
   "pos=<1,0,0>, r=1"
   "pos=<4,0,0>, r=3"
   "pos=<0,2,0>, r=1"
   "pos=<0,5,0>, r=3"
   "pos=<0,0,3>, r=1"
   "pos=<1,1,1>, r=1"
   "pos=<1,1,2>, r=1"
   "pos=<1,3,1>, r=1"])

(deftest test1
  (is (= 7 (solve1 test-input)))
  (is (= 619 (solve1 raw-input))))
