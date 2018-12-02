(ns aoc2018.02-test
  (:require [aoc2018.02 :refer [solve1 solve2]]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def ids (->> "02" io/resource io/reader line-seq))

(deftest test1
  (is (= 12 (solve1 ["abcdef"
                     "bababc"
                     "abbcde"
                     "abcccd"
                     "aabcdd"
                     "abcdee"
                     "ababab"])))
  (is (= 6723 (solve1 ids))))

(deftest test2
  (is (= ["fghij" "fguij"]
         (solve2 ["abcde"
                  "fghij"
                  "klmno"
                  "pqrst"
                  "fguij"
                  "axcye"
                  "wvxyz"])))
  (is (= ["prtkqyluiusocwvaezjmhmfbgx" "prtkqyluiusocwvaezjmhmfngx"]
         (solve2 ids))))
