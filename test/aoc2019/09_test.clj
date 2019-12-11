(ns aoc2019.09-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.09 :refer [solve1 solve2]]))

(def data (->> "2019/09" io/resource slurp))

(deftest test1
  (is (= (solve1 data) 2738720997)))

(deftest test2
  (is (= (solve2 data) 50894)))
