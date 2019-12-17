(ns aoc2019.17-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.17 :refer [solve1 solve2]]))

(def data (->> "2019/17" io/resource slurp))

(deftest test1
  (is (= (solve1 data) 6052)))
