(ns aoc2021.06-test
  (:require [aoc2021.06 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "3,4,3,1,2")

(def data (->> "2021/06" io/resource slurp))

(deftest test1
  (is (= 5934 (solve1 example)))
  (is (= 362639 (solve1 data))))

(deftest test2
  (is (= 26984457539 (solve2 example)))
  (is (= 1639854996917 (solve2 data))))
