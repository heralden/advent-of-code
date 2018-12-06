(ns aoc2018.06-test
  (:require [aoc2018.06 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def raw-points (->> "06" io/resource io/reader line-seq))

(def test-points ["1, 1"
                  "1, 6"
                  "8, 3"
                  "3, 4"
                  "5, 5"
                  "8, 9"])

(deftest test1
  (is (= 17 (solve1 test-points)))
  (is (= 5941 (solve1 raw-points))))

(deftest test2
  (is (= 16 (solve2 32 test-points)))
  (is (= 40244 (solve2 10000 raw-points))))
