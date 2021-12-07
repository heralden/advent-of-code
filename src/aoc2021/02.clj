(ns aoc2021.02
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

(defn acc1 [[x y] [cmd units]]
  (let [n (edn/read-string units)]
    (case cmd
      "forward" [(+ x n) y]
      "down" [x (+ y n)]
      "up" [x (- y n)])))

(defn solve1 [data]
  (->> (str/split data #"\s") (partition 2) (reduce acc1 [0 0]) (apply *)))

(defn acc2 [[x y a] [cmd units]]
  (let [n (edn/read-string units)]
    (case cmd
      "forward" [(+ x n) (+ y (* a n)) a]
      "down" [x y (+ a n)]
      "up" [x y (- a n)])))

(defn solve2 [data]
  (->> (str/split data #"\s") (partition 2) (reduce acc2 [0 0 0]) (take 2) (apply *)))
