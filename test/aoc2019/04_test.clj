(ns aoc2019.04-test
  (:require [clojure.test :refer [deftest is]]
            [aoc2019.04 :refer [solve1 valid1? solve2 valid2?]]))

(def data [165432 707912])

(deftest test1
  (is (true? (valid1? 111111)))
  (is (false? (valid1? 223450)))
  (is (false? (valid1? 123789)))
  (is (= (solve1 data) 1716)))

(deftest test2
  (is (true? (valid2? 112233)))
  (is (false? (valid2? 123444)))
  (is (true? (valid2? 111122)))
  (is (= (solve2 data) 1163)))
