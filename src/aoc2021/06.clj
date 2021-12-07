(ns aoc2021.06
  (:require [clojure.string :as str]))

#_(defn lanternfish-cycle [state _]
    (reduce (fn [state [index timer]]
              (if (zero? timer)
                (-> state
                    (assoc index 6)
                    (conj 8))
                (update state index dec)))
            state
            (map-indexed vector state)))

(defn lanternfish-cycle [state _]
  (let [babies (first state)]
     (-> (subvec state 1)
         (conj babies)
         (update 6 + babies))))

(defn simulate [days data]
    (let [state (->> (mapv #(Integer/parseInt %) (str/split data #"[,\s]"))
                     (reduce #(update %1 %2 inc) (vec (repeat 9 0))))]
      (->> (reduce lanternfish-cycle state (range days))
           (reduce +))))

(def solve1 (partial simulate 80))
(def solve2 (partial simulate 256))
