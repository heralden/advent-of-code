(ns aoc2018.03-test
  (:require [aoc2018.03 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def claims (->> "2018/03" io/resource io/reader line-seq))

(def test-claims ["#1 @ 1,3: 4x4"
                  "#2 @ 3,1: 4x4"
                  "#3 @ 5,5: 2x2"])

(deftest test1
  (is (= 4 (solve1 test-claims)))
  (is (= 111266 (solve1 claims))))

(deftest test2
  (is (= 3 (solve2 test-claims)))
  (is (= 266 (solve2 claims))))
