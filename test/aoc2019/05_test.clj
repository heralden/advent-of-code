(ns aoc2019.05-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.05 :refer [solve1 solve2]]))

(def data (->> "2019/05" io/resource slurp))

(deftest test1
  (is (= (solve1 data) 16489636)))

(deftest test2
  (is (= (solve2 data) 9386583)))
