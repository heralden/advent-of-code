(ns aoc2018.01-test
  (:require [aoc2018.01 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(def data (->> "2018/01" io/resource slurp))

(deftest test1
  (is (= 3 (solve1 "+1\n-2\n+3\n+1")))
  (are [in exp] (= exp (solve1 in))
    "+1\n+1\n+1" 3
    "+1\n+1\n-2" 0
    "-1\n-2\n-3" -6)
  (is (= 433 (solve1 data))))

(deftest test2
  (is (= 2 (solve2 "+1\n-2\n+3\n+1\n+1\n-2")))
  (are [in exp] (= exp (solve2 in))
    "+1\n-1" 0
    "+3\n+3\n+4\n-2\n-4" 10
    "-6\n+3\n+8\n+5\n-6" 5
    "+7\n+7\n-2\n-7\n-4" 14)
  (is (= 256 (solve2 data))))
