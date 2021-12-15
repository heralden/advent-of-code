(ns aoc2021.13-test
  (:require [aoc2021.13 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def example "6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5")

(def data (->> "2021/13" io/resource slurp))

(deftest test1
  (is (= 17 (solve1 example)))
  (is (= 802 (solve1 data))))

(deftest test2
  (is (= (solve2 example) "\n#####\n#...#\n#...#\n#...#\n#####"))
  (is (= (solve2 data) "\n###..#..#.#..#.####.####..##..#..#.###.\n#..#.#.#..#..#.#.......#.#..#.#..#.#..#\n#..#.##...####.###....#..#....#..#.###.\n###..#.#..#..#.#.....#...#.##.#..#.#..#\n#.#..#.#..#..#.#....#....#..#.#..#.#..#\n#..#.#..#.#..#.#....####..###..##..###.")))
