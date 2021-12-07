(ns aoc2021.03-test
  (:require [aoc2021.03 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010")

(def data (->> "2021/03" io/resource slurp))

(deftest test1
  (is (= 198 (solve1 example)))
  (is (= 3242606 (solve1 data))))

(deftest test2
  (is (= 230 (solve2 example)))
  (is (= 4856080 (solve2 data))))
