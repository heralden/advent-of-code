(ns aoc2019.13-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.13 :refer [solve1 solve2]]))

(def data (->> "2019/13" io/resource slurp))

(deftest test1
  (is (= (solve1 data) 280)))

(deftest test2
  (is (= (solve2 data) 13298)))
