(ns aoc2019.06-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.06 :refer [solve1 solve2]]))

(def data (->> "2019/06" io/resource slurp))

(deftest test1
  (is (= (solve1 "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L") 42))
  (is (= (solve1 data) 261306)))

(deftest test2
  (is (= (solve2 "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN") 4))
  (is (= (solve2 data) 382)))
