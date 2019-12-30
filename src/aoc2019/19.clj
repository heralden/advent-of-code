(ns aoc2019.19
  (:require [aoc2019.intcode :as intcode]
            [clojure.core.async :refer [chan >!! <!!]]))

(defn computer [program]
  (let [in-ch (chan)
        out-ch (chan)
        ctrl-ch (chan)]
    (fn [[x y]]
      (intcode/process program in-ch out-ch ctrl-ch)
      (>!! in-ch x)
      (>!! in-ch y)
      (<!! out-ch))))

(defn print-beam [w h beam]
  (let [beam? (set beam)]
    (->> (for [y (range h)
               x (range w)]
           (str (when (zero? x) \newline)
                (if (beam? [x y]) \# \.)))
         (println))))

(defn build-beam [program]
  (for [y (range 50)
        x (range 50)
        :let [c (computer program)]
        :when (pos? (c [x y]))]
    [x y]))

(defn solve1 [program]
  (count (build-beam program)))

(comment
  (def program (->> "2019/19" clojure.java.io/resource slurp))
  (print-beam 50 50 (build-beam program)))

(defn corner-pair [all-edges corner]
  (when-let [other (first (filter #(contains? all-edges (apply mapv + [corner %]))
                                  [[-99 99] [99 -99]]))]
    [corner (apply mapv + [corner other])]))

(defn solve2 [program]
  (let [in-ch (chan)
        out-ch (chan)
        halt-ch (chan)
        ctrl-ch (chan)]
    (intcode/process program in-ch out-ch halt-ch ctrl-ch)
    (loop [pos [6 6] ; manually add position x-1 of first beam of 2+ width
           pedges [nil nil]
           edges [nil nil]
           all-edges #{}]
      (let [beam? (pos? (let [res (do (>!! in-ch (get pos 0))
                                      (>!! in-ch (get pos 1))
                                      (<!! out-ch))]
                          (<!! halt-ch)
                          (>!! ctrl-ch :reset)
                          res))
             no-edges? (empty? (keep not-empty edges))
             pos' (cond
                    (and (not no-edges?) (not beam?)) (update (first edges) 1 inc)
                    (and beam? no-edges? (every? not-empty pedges)) (assoc pos 0 (first (second pedges)))
                    :else (update pos 0 inc))
             edges' (cond
                      (and beam? no-edges?) (assoc edges 0 pos)
                      beam? (assoc edges 1 pos)
                      :else edges)
             pedges' (cond
                       (and (not no-edges?) (not beam?)) edges'
                       :else pedges)
             all-edges' (cond
                          (and (not no-edges?) (not beam?)) (into all-edges edges')
                          :else all-edges)]
        (if-let [[[x _] [_ y]]
                 (and (not no-edges?) (not beam?)
                      (some identity (map (partial corner-pair all-edges) edges')))]
           (+ (* x 10000) y)
           (recur pos'
                  pedges'
                  (if (and (not no-edges?) (not beam?))
                    [nil nil]
                    edges')
                  all-edges'))))))
