(ns aoc2019.03-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]
            [aoc2019.03 :refer [solve1 solve2]]))

(def data (->> "2019/03" io/resource slurp))

(deftest test1
  (is (= (solve1 "R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83") 159))
  (is (= (solve1 "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
U98,R91,D20,R16,D67,R40,U7,R15,U6,R7") 135))
  (is (= (solve1 data) 352)))

(deftest test2
  (is (= (solve2 "R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83") 610))
  (is (= (solve2 "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
U98,R91,D20,R16,D67,R40,U7,R15,U6,R7") 410))
  (is (= (solve2 data) 43848)))
