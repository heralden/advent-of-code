(ns aoc2019.16
  (:require [clojure.string :as string]))

(comment
  (def data (->> "2019/16" clojure.java.io/resource slurp)))

(defn read-input [data]
  (map #(Integer/parseInt %)
       (string/split (first (string/split-lines data)) #"")))

(defn derived-pattern [index pattern]
  (drop 1 (cycle (apply interleave (repeat index pattern)))))

(defn phase-step [input pattern]
  (Math/abs (rem (reduce + (map * input pattern)) 10)))

(let [pattern [0 1 0 -1]]
  (defn phase [input _]
    (map-indexed
      (fn [i _]
        (phase-step input (derived-pattern (inc i) pattern)))
      input)))

(defn solve1 [data]
  (let [input (read-input data)]
    (string/join (take 8 (reduce phase input (range 100))))))

(defn solve2 [data]
  (let [input (apply concat (repeat 10000 (read-input data)))
        offset (Integer/parseInt (string/join (take 7 input)))]
    (string/join (take 8 (drop offset (reduce phase input (range 100)))))))
