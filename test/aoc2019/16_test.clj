(ns aoc2019.16-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.16 :refer [solve1 solve2]]))

(def data (->> "2019/16" io/resource slurp))

(deftest test1
  (is (= (solve1 "80871224585914546619083218645595") "24176176"))
  (is (= (solve1 "19617804207202209144916044189917") "73745418"))
  (is (= (solve1 "69317163492948606335995924319873") "52432133"))
  (is (= (solve1 data) "76795888")))

#_(deftest test2
    (is (= (solve2 "03036732577212944063491565474664") "84462026")))
