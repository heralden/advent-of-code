(ns aoc2019.03
  (:require [clojure.string :as string]
            [clojure.set :refer [intersection]]))

(defn line-wire [coords [dir steps]]
  (let [pos (last coords)
        step-f #(->> (range steps)
                     (reductions
                       (fn [p _] (update p %1 %2))
                       pos)
                     (drop 1))]
    (into coords
          (case dir
            "U" (step-f 1 inc)
            "D" (step-f 1 dec)
            "R" (step-f 0 inc)
            "L" (step-f 0 dec)))))

(defn trace-wire [path]
  (reduce line-wire [[0 0]] path))

(defn read-path [data]
  (->> (string/split data #",")
       (map #(let [[_ dir n] (re-find #"([A-Z])(\d+)" %)]
               [dir (read-string n)]))))

(defn solve1 [data]
  (let [[path1 path2] (map read-path (string/split-lines data))
        coords1 (set (drop 1 (trace-wire path1)))
        coords2 (set (drop 1 (trace-wire path2)))]
    (->> (intersection coords1 coords2)
         (map (fn [coord]
                (reduce + (map #(Math/abs %) coord))))
         (reduce min))))

(defn solve2 [data]
  (let [[path1 path2] (map read-path (string/split-lines data))
        trace1 (drop 1 (trace-wire path1))
        trace2 (drop 1 (trace-wire path2))]
    (->> (intersection (set trace1) (set trace2))
         (map #(+ (.indexOf trace1 %)
                  (.indexOf trace2 %)))
         (reduce min)
         ;; Add back the starting coordinate step for both traces, which
         ;; was removed from the intersection check.
         (+ 2))))

