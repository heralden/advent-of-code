(ns aoc2021.11
  (:require [clojure.string :as str]))

(defn parse-octomap [data]
  (let [octopuses (map #(Integer/parseInt %) (re-seq #"\d" data))
        coords (for [y (range (-> data str/split-lines count))
                     x (range (-> data str/split-lines first count))]
                 [x y])]
    (zipmap coords octopuses)))

(defn adjacent-coords [[x y]]
  [[(inc x) y] [(dec x) y]
   [x (inc y)] [x (dec y)]
   [(inc x) (inc y)] [(dec x) (dec y)]
   [(inc x) (dec y)] [(dec x) (inc y)]])

(defn process-flashes [[octomap flashes fcount]]
  (if (empty? flashes)
    [octomap fcount]
    (recur (reduce (fn [[octomap new-flashes fcount] coord]
                     (let [energy (octomap coord)]
                       [(update octomap coord inc)
                        (if (= energy 9)
                          (conj new-flashes coord)
                          new-flashes)
                        fcount]))
                   [octomap [] (+ fcount (count flashes))]
                   (mapcat (comp (partial filter octomap) adjacent-coords)
                           flashes)))))

(defn simulate-step [[octomap fcount] _]
  (-> (reduce (fn [[octomap flashes] coord]
                (let [energy (octomap coord)]
                  (cond
                    (> energy 9) [(assoc octomap coord 1) flashes]
                    (= energy 9) [(update octomap coord inc) (conj flashes coord)]
                    :else [(update octomap coord inc) flashes])))
              [octomap []] (keys octomap))
      (conj fcount)
      (process-flashes)))

(defn solve1 [data]
  (let [octomap (parse-octomap data)]
    (second (reduce simulate-step [octomap 0] (range 100)))))

(defn simulate-until-megaflash [[[_ fcount :as state] prev-fcount] step]
  (if (= 100 (- fcount prev-fcount))
    (reduced step)
    [(simulate-step state step) fcount]))

(defn solve2 [data]
  (let [octomap (parse-octomap data)]
    (reduce simulate-until-megaflash [[octomap 0] 0] (range))))

;; per step
;; run 1: increase energy of each coord by 1 (if > 9, set to 0), record each coord that became 10 (flashes)
;; run 2: go through each flashes, increasing each adjacent coord by 1, record each coord that became 10
;; run 3: repeat run 2 until there are no more flashes
