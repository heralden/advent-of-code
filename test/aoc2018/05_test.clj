(ns aoc2018.05-test
  (:require [aoc2018.05 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def polymer (->> "2018/05" io/resource io/reader line-seq first))

(def test-polymer "dabAcCaCBAcCcaDA")

(deftest test1
  (is (= 10 (solve1 test-polymer)))
  (is (= 9562 (solve1 polymer))))

(deftest test2
  (is (= 4 (solve2 test-polymer)))
  (is (= 4934 (solve2 polymer))))
