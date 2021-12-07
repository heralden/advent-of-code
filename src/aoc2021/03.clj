(ns aoc2021.03
  (:require [clojure.string :as str]))

(defn most-common [v i]
  (apply max-key val (frequencies (map #(nth % i) v))))

(defn flip-bit [bit]
  (case bit \1 \0 \0 \1))

(defn gamma-rate [in]
  (map first (map (partial most-common in) (-> in first count range))))

(defn bin [bits]
  (Integer/parseInt (apply str bits) 2))

(defn solve1 [data]
  (let [in (str/split-lines data)
        gamma (gamma-rate in)
        epsilon (map flip-bit gamma)]
    (* (bin gamma) (bin epsilon))))

(defn common [v i not-found k]
  (let [freqs (frequencies (map #(nth % i) v))]
    (if (apply = (vals freqs))
      not-found
      (key (apply k val freqs)))))

(defn knockout [not-found k in i]
  (let [keep-bit (common in i not-found k)]
    (filter #(= keep-bit (nth % i)) in)))

(defn x-rating [not-found k in]
  (reduce #(if (> (count %1) 1)
             (knockout not-found k %1 %2)
             (reduced %1))
          in (-> in first count range)))

(defn solve2 [data]
  (let [in (str/split-lines data)
        oxygen (x-rating \1 max-key in)
        co2 (x-rating \0 min-key in)]
    (* (bin oxygen) (bin co2))))
