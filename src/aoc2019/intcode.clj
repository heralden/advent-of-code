(ns aoc2019.intcode
  (:require [clojure.string :as string]
            [clojure.core.async :refer [go-loop <! >!]]))

(defn read-intcodes [intcodes]
  (zipmap (range) (mapv read-string (string/split intcodes #","))))

(defn process [intcodes in-ch out-ch halt-ch & [ctrl-ch]]
  (let [program (read-intcodes intcodes)]
    (go-loop [prog program
              pos 0
              rel 0]
      (let [code (get prog pos 0)
            r1   (get prog (+ pos 1) 0)
            r2   (get prog (+ pos 2) 0)
            rout (get prog (+ pos 3) 0)
            v1 (get prog r1 0)
            v2 (get prog r2 0)
            [m3 m2 m1 & i] (->> (format "%05d" code)
                                (map (comp read-string str)))
            instr (Integer/parseInt (string/join i))
            rel1 (get prog (+ rel r1) 0)
            rel2 (get prog (+ rel r2) 0)
            f1 (case m1 2 rel1 1 r1 v1)
            f2 (case m2 2 rel2 1 r2 v2)
            fout (case m3 2 (+ rel rout) rout)]
        (case instr
          1 (recur (assoc prog fout (+ f1 f2)) (+ pos 4) rel)
          2 (recur (assoc prog fout (* f1 f2)) (+ pos 4) rel)
          3 (recur (assoc prog (case m1 2 (+ rel r1) r1) (<! in-ch)) (+ pos 2) rel)
          4 (do (>! out-ch f1) (recur prog (+ pos 2) rel))
          5 (recur prog (if (not (zero? f1)) f2 (+ pos 3)) rel)
          6 (recur prog (if (zero? f1) f2 (+ pos 3)) rel)
          7 (recur (assoc prog fout (if (< f1 f2) 1 0)) (+ pos 4) rel)
          8 (recur (assoc prog fout (if (= f1 f2) 1 0)) (+ pos 4) rel)
          9 (recur prog (+ pos 2) (+ rel f1))
          99 (do (>! halt-ch false)
                 (case (<! ctrl-ch)
                   :reset (recur program 0 0)
                   nil)))))))
