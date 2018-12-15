(ns aoc2018.14
  (:require [clojure.string :refer [split join]]))

(defn score-recipe [x]
  (->> (split (str x) #"")
       (mapv read-string)))

;; `limit` can be either a vector of a score sequence to match, or a number for
;; amount of recipes.
(defn bake [limit [recipes elf1 elf2] _]
  (let [[prev1 prev2] (map #(get recipes %) [elf1 elf2])
        [new1 new2] (score-recipe (+ prev1 prev2))
        new-recipes (if (some? new2)
                      (conj recipes new1 new2)
                      (conj recipes new1))
        len (count new-recipes)]
    (if (if (number? limit)
          (>= len limit)
          (and (>= len (count limit))
               (= (subvec new-recipes (- len (count limit))) limit)))
      (reduced (if (number? limit) new-recipes (- len (count limit))))
      [new-recipes
       (mod (+ elf1 (inc prev1)) len)
       (mod (+ elf2 (inc prev2)) len)])))

(defn solve1 [attempts]
  (let [init [[3 7 1 0] 0 1]
        limit (+ attempts 10)]
    (->> (reduce (partial bake limit) init (range))
         (drop attempts)
         (take 10)
         join)))

(defn solve2 [recipes]
  (let [init [[3 7 1 0] 0 1]
        limit (score-recipe recipes)]
    (reduce (partial bake limit) init (range))))
