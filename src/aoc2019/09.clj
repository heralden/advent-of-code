(ns aoc2019.09
  (:require [clojure.string :as string]))

(defn read-intcodes [intcodes]
  (zipmap (range) (mapv read-string (string/split intcodes #","))))

(defn process [input [program pos rel] _]
  (let [code (get program pos 0)
        r1   (get program (+ pos 1) 0)
        r2   (get program (+ pos 2) 0)
        rout (get program (+ pos 3) 0)
        v1 (get program r1 0)
        v2 (get program r2 0)
        [m3 m2 m1 _ instr] (->> (format "%05d" code)
                                (map (comp read-string str)))
        rel1 (get program (+ rel r1) 0)
        rel2 (get program (+ rel r2) 0)
        f1 (case m1 2 rel1 1 r1 v1)
        f2 (case m2 2 rel2 1 r2 v2)
        fout (case m3 2 (+ rel rout) rout)]
    (case instr
      1 [(assoc program fout (+ f1 f2)) (+ pos 4) rel]
      2 [(assoc program fout (* f1 f2)) (+ pos 4) rel]
      3 [(assoc program (case m1 2 (+ rel r1) r1) input) (+ pos 2) rel]
      4 (reduced f1)
      5 [program (if (not (zero? f1)) f2 (+ pos 3)) rel]
      6 [program (if (zero? f1) f2 (+ pos 3)) rel]
      7 [(assoc program fout (if (< f1 f2) 1 0)) (+ pos 4) rel]
      8 [(assoc program fout (if (= f1 f2) 1 0)) (+ pos 4) rel]
      9 [program (+ pos 2) (+ rel f1)])))

(defn run [program input]
  (reduce (partial process input) [program 0 0] (range)))

(defn solve1 [intcodes]
  (let [program (read-intcodes intcodes)]
    (run program 1)))

(defn solve2 [intcodes]
  (let [program (read-intcodes intcodes)]
    (run program 2)))
