(ns aoc2019.11-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.11 :refer [solve1 solve2]]))

(def data (->> "2019/11" clojure.java.io/resource slurp))

(deftest test1
  (is (= (solve1 data) 1885)))

(deftest test2
  (is (= (solve2 data) '((\. \. \# \# \# \. \. \# \. \. \. \. \# \# \# \# \. \# \. \. \# \. \. \# \# \# \. \# \. \. \# \. \# \. \. \# \. \# \. \. \.)
                         (\. \. \# \. \. \# \. \# \. \. \. \. \# \. \. \. \. \# \. \. \# \. \# \. \. \# \. \# \. \. \# \. \# \. \. \# \. \# \. \. \.)
                         (\. \. \# \. \. \# \. \# \. \. \. \. \# \. \. \. \. \# \# \# \# \. \# \. \# \# \. \# \. \. \# \. \# \# \# \# \. \# \. \. \.)
                         (\. \. \# \# \# \. \. \# \# \# \. \. \# \# \# \. \. \# \. \. \# \. \# \. \. \. \. \# \# \# \# \. \# \. \. \# \. \# \# \# \.)
                         (\. \. \# \. \. \# \. \# \. \. \. \. \# \. \. \. \. \# \. \. \# \. \# \. \. \# \. \# \. \. \# \. \# \. \. \# \. \# \. \. \.)
                         (\. \. \# \# \# \. \. \# \# \# \# \. \# \# \# \# \. \. \# \# \. \. \. \# \# \. \. \# \. \. \# \. \. \# \# \. \. \# \# \# \#)))))
