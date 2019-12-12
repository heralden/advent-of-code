(ns aoc2019.12-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :as io]
            [aoc2019.12 :refer [solve1 solve2]]))

(def data (->> "2019/12" io/resource slurp))

(deftest test1
  (is (= 179 (solve1 10 "<x=-1, y=0, z=2>
<x=2, y=-10, z=-7>
<x=4, y=-8, z=8>
<x=3, y=5, z=-1>")))
  (is (= 1940 (solve1 100 "<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>")))
  (is (= 8454 (solve1 1000 data))))

#_(deftest test2
    (is (= 2772 (solve2 "<x=-1, y=0, z=2>
<x=2, y=-10, z=-7>
<x=4, y=-8, z=8>
<x=3, y=5, z=-1>")))
    (is (= 4686774924 (solve2 "<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>")))
    (is (= 0 (solve2 data))))
