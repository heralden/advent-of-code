(ns aoc2021.13
  (:require [clojure.string :as str]))

(defn fold [dots {fold-x :x fold-y :y}]
  (into #{}
        (map (fn [[x y :as dot]]
               (if (cond
                     fold-x (> x fold-x)
                     fold-y (> y fold-y))
                 [(cond-> x fold-x (-> (- (* 2 fold-x)) (Math/abs)))
                  (cond-> y fold-y (-> (- (* 2 fold-y)) (Math/abs)))]
                 dot))
             dots)))

(defn solve [f data]
  (let [lines (str/split-lines data)
        dots (->> (take-while not-empty lines)
                  (map (comp (partial mapv #(Integer/parseInt %))
                             #(str/split % #","))))
        folds (->> (drop-while not-empty lines)
                   (mapcat #(re-seq #"([xy])=(\d+)" %))
                   (map (fn [[_ axis number]]
                          {(keyword axis) (Integer/parseInt number)})))]
    (f dots folds)))

(defn draw-dots [dots]
  (apply str (for [y (range (inc (apply max (map second dots))))
                   x (range (inc (apply max (map first dots))))]
               (str (when (zero? x) \newline)
                    (if (contains? dots [x y]) \# \.)))))

(def solve1 (partial solve (fn [dots folds]
                             (count (reduce fold dots (take 1 folds))))))
(def solve2 (partial solve (fn [dots folds]
                             (draw-dots (reduce fold dots folds)))))
