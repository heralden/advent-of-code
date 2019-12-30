(ns aoc2019.19-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.19 :refer [solve1 solve2]]))

(def data (->> "2019/19" io/resource slurp))

(deftest test1
  (is (= (solve1 data) 231)))

(deftest test2
  (is (= (solve2 data) 9210745)))
