(ns aoc2019.01-test
  (:require [aoc2019.01 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]
            [clojure.string :as string]))

(def data (->> "2019/01" io/resource slurp string/split-lines (map read-string)))

(deftest test1
  (is (= 2 (solve1 [12])))
  (is (= 2 (solve1 [14])))
  (is (= 654 (solve1 [1969])))
  (is (= 33583 (solve1 [100756])))
  (is (= 3375962 (solve1 data))))

(deftest test2
  (is (= 2 (solve2 [14])))
  (is (= 966 (solve2 [1969])))
  (is (= 50346 (solve2 [100756])))
  (is (= 5061072 (solve2 data))))
