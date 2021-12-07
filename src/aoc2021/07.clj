(ns aoc2021.07
  (:require [clojure.string :as str]))

(defn cost-for-position [cost-f positions target]
  (reduce + (map (partial cost-f target) positions)))

(defn solve [cost-f data]
  (let [positions (map #(Integer/parseInt %) (str/split data #"[,\s]"))]
    (->> (range (apply min positions) (inc (apply max positions)))
         (map (partial cost-for-position cost-f positions))
         (apply min)
         (int))))

(def solve1 (partial solve (fn [target pos] (Math/abs (- pos target)))))
(def solve2 (partial solve (fn [target pos]
                             (let [n (Math/abs (- pos target))]
                               (* n (+ 1 (* 0.5 (- n 1))))))))
