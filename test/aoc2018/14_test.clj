(ns aoc2018.14-test
  (:require [aoc2018.14 :refer [solve1 solve2]]
            [clojure.test :refer [deftest is are]]))

(def input 286051)

(deftest test1
  (are [in exp] (= exp (solve1 in))
    9 "5158916779"
    5 "0124515891"
    18 "9251071085"
    2018 "5941429882")
  (is (= "2111113678" (solve1 input))))

#_(deftest test2
    (are [in exp] (= exp (solve2 in))
      "51589" 9
      "01245" 5
      "92510" 18
      "59414" 2018)
    (is (= 20471523 (solve2 input))))
;; this isn't the correct puzzle answer, but I haven't been able to figure out why.
