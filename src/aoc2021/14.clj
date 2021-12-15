(ns aoc2021.14
  (:require [clojure.string :as str]))

(defn polymerize [insertions [init-polymers init-monomers] _]
  (reduce (fn [[polymers monomers] [left right :as polymer]]
            (let [amount (init-polymers polymer)
                  middle (insertions polymer)]
              [(-> polymers
                   (update polymer - amount)
                   (update (str left middle) (fnil + 0) amount)
                   (update (str middle right) (fnil + 0) amount))
               (-> monomers
                   (update middle (fnil + 0) amount))]))
          [init-polymers init-monomers]
          (keep (fn [[polymer amount]]
                  (when (pos? amount) polymer)) init-polymers)))

(defn solve [steps data]
  (let [lines (str/split-lines data)
        polymers (->> (first lines)
                      (partition 2 1)
                      (reduce (fn [m [left right]]
                                (update m (str left right) (fnil inc 0))) {}))
        monomers (frequencies (first lines))
        insertions (->> (drop 2 lines)
                        (map #(str/split % #" -> "))
                        (map (juxt (comp identity first) (comp first second)))
                        (into {}))]
    (->> (reduce (partial polymerize insertions) [polymers monomers] (range steps))
         (second) (vals) ((juxt #(apply max %) #(apply min %))) (apply -))))

(def solve1 (partial solve 10))
(def solve2 (partial solve 40))

;; polymers {"CH" 3 "HB" 5}
;; monomers {\B 1 \H 4}
;; NN -> poly NC CN mono N C N
;; NC -> poly NB BC mono N B C
;; CN -> poly CC CN mono C C N
