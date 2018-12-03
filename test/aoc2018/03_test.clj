(ns aoc2018.03-test
  (:require [aoc2018.03 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def ids (->> "03" io/resource io/reader line-seq))

(deftest test1
  (is (= 4 (solve1 ["#1 @ 1,3: 4x4"
                    "#2 @ 3,1: 4x4"
                    "#3 @ 5,5: 2x2"]))))
