(ns aoc2021.07-test
  (:require [aoc2021.07 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "16,1,2,0,4,2,7,1,2,14")

(def data (->> "2021/07" io/resource slurp))

(deftest test1
  (is (= 37 (solve1 example)))
  (is (= 352707 (solve1 data))))

(deftest test2
  (is (= 168 (solve2 example)))
  (is (= 95519693 (solve2 data))))
