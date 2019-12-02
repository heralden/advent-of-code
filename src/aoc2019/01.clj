(ns aoc2019.01)

(defn fuel [mass]
  (- (Math/floor (/ mass 3)) 2))

(defn solve1 [masses]
  (->> (map fuel masses)
       (reduce +)
       (int)))

(defn combined-fuel [mass]
  (->> (iterate fuel mass)
       (drop 1)
       (take-while pos?)
       (reduce +)))

(defn solve2 [masses]
  (->> (map combined-fuel masses)
       (reduce +)
       (int)))
