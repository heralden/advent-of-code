(ns aoc2018.25-test
  (:require [aoc2018.25 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-input (->> "25" io/resource io/reader line-seq))

(def test-input1
  ["-1,2,2,0"
   "0,0,2,-2"
   "0,0,0,-2"
   "-1,2,0,0"
   "-2,-2,-2,2"
   "3,0,2,-1"
   "-1,3,2,2"
   "-1,0,-1,0"
   "0,2,1,-2"
   "3,0,0,0"])

(def test-input2
  ["1,-1,0,1"
   "2,0,-1,0"
   "3,2,-1,0"
   "0,0,3,1"
   "0,0,-1,-1"
   "2,3,-2,0"
   "-2,2,0,0"
   "2,-2,0,-1"
   "1,-1,0,-1"
   "3,2,0,2"])

(def test-input3
  ["1,-1,-1,-2"
   "-2,-2,0,1"
   "0,2,1,3"
   "-2,3,-2,1"
   "0,2,3,-2"
   "-1,-1,1,-2"
   "0,-2,-1,0"
   "-2,2,3,-1"
   "1,2,2,0"
   "-1,-2,0,-2"])

(deftest test1
  (are [in exp] (= exp (solve1 in))
    test-input1 4
    test-input2 3
    test-input3 8))
  (is (= 363 (solve1 raw-input)))
