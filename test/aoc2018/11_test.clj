(ns aoc2018.11-test
  (:require [aoc2018.11 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def input 9306)

(deftest test1
  (is (= "33,45" (solve1 18 300)))
  (is (= "235,38" (solve1 input 300))))

(deftest test2
  (is (= "90,269,16" (solve2 18 300 30)))
  (is (= "233,146,13" (solve2 input 300 30))))
