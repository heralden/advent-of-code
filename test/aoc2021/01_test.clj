(ns aoc2021.01-test
  (:require [aoc2021.01 :refer [solve1 solve2 parse]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "199
200
208
210
200
207
240
269
260
263")

(def data (->> "2021/01" io/resource slurp))

(deftest test1
  (is (= 7 (solve1 (parse example))))
  (is (= 1393 (solve1 (parse data)))))

(deftest test2
  (is (= 5 (solve2 (parse example))))
  (is (= 1359 (solve2 (parse data)))))
