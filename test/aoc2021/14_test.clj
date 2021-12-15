(ns aoc2021.14-test
  (:require [aoc2021.14 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C")

(def data (->> "2021/14" io/resource slurp))

(deftest test1
  (is (= 1588 (solve1 example)))
  (is (= 2602 (solve1 data))))

(deftest test2
  (is (= 2188189693529 (solve2 example)))
  (is (= 2942885922173 (solve2 data))))
