(ns aoc2021.10-test
  (:require [aoc2021.10 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]")

(def data (->> "2021/10" io/resource slurp))

(deftest test1
  (is (= 26397 (solve1 example)))
  (is (= 442131 (solve1 data))))

(deftest test2
  (is (= 288957 (solve2 example)))
  (is (= 3646451424 (solve2 data))))
