(ns aoc2018.09-test
  (:require [aoc2018.09 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-data (->> "2018/09" io/resource io/reader line-seq first))

#_(deftest test1
    (is (= 32 (solve1 "9 players; last marble is worth 32 points")))
    (are [in exp] (= exp (solve1 in))
      "10 players; last marble is worth 1618 points" 8317
      "13 players; last marble is worth 7999 points" 146373
      "17 players; last marble is worth 1104 points" 2764
      "21 players; last marble is worth 6111 points" 54718
      "30 players; last marble is worth 5807 points" 37305))
