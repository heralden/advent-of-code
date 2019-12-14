(ns aoc2018.12-test
  (:require [aoc2018.12 :refer [solve1]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def raw-data (->> "2018/12" io/resource io/reader line-seq))

(def test-data ["initial state: #..#.#..##......###...###"
                nil
                "...## => #"
                "..#.. => #"
                ".#... => #"
                ".#.#. => #"
                ".#.## => #"
                ".##.. => #"
                ".#### => #"
                "#.#.# => #"
                "#.### => #"
                "##.#. => #"
                "##.## => #"
                "###.. => #"
                "###.# => #"
                "####. => #"])

(deftest test1
  (is (= 325 (solve1 20 test-data)))
  (is (= 3120 (solve1 20 raw-data))))
