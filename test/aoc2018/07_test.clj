(ns aoc2018.07-test
  (:require [aoc2018.07 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def raw-data (->> "2018/07" io/resource io/reader line-seq))

(def test-data ["Step C must be finished before step A can begin."
                "Step C must be finished before step F can begin."
                "Step A must be finished before step B can begin."
                "Step A must be finished before step D can begin."
                "Step B must be finished before step E can begin."
                "Step D must be finished before step E can begin."
                "Step F must be finished before step E can begin."])

(deftest test1
  (is (= "CABDFE" (solve1 test-data)))
  (is (= "JMQZELVYXTIGPHFNSOADKWBRUC" (solve1 raw-data))))

(deftest test2
  (is (= 15 (solve2 2 0 test-data)))
  (is (= 1133 (solve2 5 60 raw-data))))
