(ns aoc2019.02
  (:require [clojure.string :as string]))

(defn read-intcodes [intcodes]
  (vec (->> (string/split intcodes #",")
            (map read-string))))

(defn process [program position]
  (let [[code r1 r2 rout] (subvec program position)
        v1 (get program r1)
        v2 (get program r2)]
    (case code
      1 (assoc program rout (+ v1 v2))
      2 (assoc program rout (* v1 v2))
      (reduced program))))

(defn run [program]
  (->> (range 0 (count program) 4)
       (reduce process program)))

(defn solve1 [intcodes]
  (let [program (read-intcodes intcodes)]
    (->> (run program)
         (string/join ","))))

(defn solve2 [intcodes]
  (let [program (read-intcodes intcodes)]
    (first (for [noun (range 0 100)
                 verb (range 0 100)
                 :let [result (-> program
                                  (assoc 1 noun)
                                  (assoc 2 verb)
                                  run)]
                 :when (= (get result 0) 19690720)]
             (+ (* 100 noun) verb)))))
