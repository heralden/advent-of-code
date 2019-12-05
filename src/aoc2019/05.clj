(ns aoc2019.05
  (:require [clojure.string :as string]))

(defn read-intcodes [intcodes]
  (mapv read-string (string/split intcodes #",")))

(defn process [input [program pos] _]
  (let [[code r1 r2 rout] (subvec program pos)
        v1 (get program r1)
        v2 (get program r2)
        [m2 m1 _ instr] (->> (format "%04d" code)
                             (map (comp read-string str)))
        f1 (if (= m1 1) r1 v1)
        f2 (if (= m2 1) r2 v2)]
    (case instr
      1 [(assoc program rout (+ f1 f2)) (+ pos 4)]
      2 [(assoc program rout (* f1 f2)) (+ pos 4)]
      3 [(assoc program r1 input) (+ pos 2)]
      4 (if (zero? f1) [program (+ pos 2)] (reduced f1))
      5 [program (if (not (zero? f1)) f2 (+ pos 3))]
      6 [program (if (zero? f1) f2 (+ pos 3))]
      7 [(assoc program rout (if (< f1 f2) 1 0)) (+ pos 4)]
      8 [(assoc program rout (if (= f1 f2) 1 0)) (+ pos 4)])))

(defn run [program input]
  (reduce (partial process input) [program 0] (range)))

(defn solve1 [intcodes]
  (let [program (read-intcodes intcodes)]
    (run program 1)))

(defn solve2 [intcodes]
  (let [program (read-intcodes intcodes)]
    (run program 5)))
