(ns aoc2021.04
  (:require [clojure.string :as str]))

(def board-size 25)
(def board-width 5)

(defn marked? [tile]
  (true? (second tile)))

(defn winning-board? [board]
  (or (some #(every? marked? %) (partition board-width board))
      (->> (map #(take-nth board-width (drop % board)) (range board-width))
           (some #(every? marked? %)))))

(defn mark [tile]
  (update tile 1 not))

(defn mark-board [board number]
  (map (fn [tile]
         (if (= number (first tile))
           (mark tile)
           tile))
       board))

(defn score-board [board number]
  (* number (reduce + (map first (filter (complement marked?) board)))))

(defn reduce-bingo [f data]
  (let [[numbers-string & all-boards] (str/split data #"\s+")
        numbers (map #(Integer/parseInt %) (str/split numbers-string #","))
        boards (partition board-size (map #(vector (Integer/parseInt %) false) all-boards))]
    (reduce f boards numbers)))

(defn solve1 [data]
  (reduce-bingo (fn [boards number]
                  (let [marked-boards (map #(mark-board % number) boards)]
                    (if-let [score (some #(when (winning-board? %)
                                            (score-board % number))
                                         marked-boards)]
                      (reduced score)
                      marked-boards)))
                data))

(defn solve2 [data]
  (reduce-bingo (fn [boards number]
                  (let [marked-boards (map #(mark-board % number) boards)
                        nonwinning-boards (remove winning-board? marked-boards)]
                    (if (seq nonwinning-boards)
                      nonwinning-boards
                      (reduced (score-board (first marked-boards) number)))))
                data))
