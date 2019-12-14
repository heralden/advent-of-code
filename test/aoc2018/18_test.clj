(ns aoc2018.18-test
  (:require [aoc2018.18 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-area (->> "2018/18" io/resource io/reader line-seq))

(def test-area [".#.#...|#."
                ".....#|##|"
                ".|..|...#."
                "..|#.....#"
                "#.#|||#|#|"
                "...#.||..."
                ".|....|..."
                "||...#|.#|"
                "|.||||..|."
                "...#.|..|."])

(deftest test1
  (is (= 1147 (solve1 test-area 10)))
  (is (= 535522 (solve1 raw-area 10))))

