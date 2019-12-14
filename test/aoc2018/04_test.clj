(ns aoc2018.04-test
  (:require [aoc2018.04 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def records (->> "2018/04" io/resource io/reader line-seq))

(def test-records
  ["[1518-11-01 00:00] Guard #10 begins shift"
   "[1518-11-01 00:05] falls asleep"
   "[1518-11-01 00:25] wakes up"
   "[1518-11-01 00:30] falls asleep"
   "[1518-11-01 00:55] wakes up"
   "[1518-11-01 23:58] Guard #99 begins shift"
   "[1518-11-02 00:40] falls asleep"
   "[1518-11-02 00:50] wakes up"
   "[1518-11-03 00:05] Guard #10 begins shift"
   "[1518-11-03 00:24] falls asleep"
   "[1518-11-03 00:29] wakes up"
   "[1518-11-04 00:02] Guard #99 begins shift"
   "[1518-11-04 00:36] falls asleep"
   "[1518-11-04 00:46] wakes up"
   "[1518-11-05 00:03] Guard #99 begins shift"
   "[1518-11-05 00:45] falls asleep"
   "[1518-11-05 00:55] wakes up"])

(deftest test1
  (is (= 240 (solve1 test-records)))
  (is (= 140932 (solve1 records))))

(deftest test2
  (is (= 4455 (solve2 test-records)))
  (is (= 51232 (solve2 records))))
