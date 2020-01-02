(ns aoc2019.22
  (:require [clojure.string :as string]))

(defn read-input [input]
  (map #(let [[_ tech n] (re-find #"([a-z\s]+)(-?\d*)" %)]
          (cond-> [(string/trimr tech)]
            (not-empty n) (conj (Integer/parseInt n))))
       (string/split-lines input)))

(defn shuffle-tech [deck [tech n]]
  (case tech
    "deal into new stack"
    (vec (reverse deck))
    "cut"
    (->> (split-at (if (pos? n) n (+ (count deck) n)) deck)
         (reverse)
         (into [] cat))
    "deal with increment"
    (second
     (reduce (fn [[deck deck'] i]
               [(subvec deck 1) (assoc deck' i (first deck))])
             [deck deck]
             (take (count deck)
                   (map #(mod (* % n) (count deck))
                        (range)))))))

(defn solve1 [cards input & {:keys [grab]}]
  (let [shuffling (read-input input)
        deck (vec (range cards))]
    (cond-> (reduce shuffle-tech deck shuffling)
      (number? grab) (.indexOf grab))))

;; There's no way this is gonna work without some heavy math copy-pasting.
(defn solve2 [cards times input & {:keys [grab-n]}]
  (let [shuffling (read-input input)
        deck (vec (range cards))]
    (cond-> (reduce shuffle-tech deck (take (* (count shuffling) times)
                                            (cycle shuffling)))
      (number? grab-n) (nth grab-n))))
