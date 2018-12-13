(ns aoc2018.12
  (:require [clojure.set :refer [difference]]))

;; You should probably use `vectors` and `subvec` instead of `sets` and
;; `contains?` to get it optimized enough for part 2. Remember that one rule
;; can only return one living, if there's space for optimization there?

(set! *unchecked-math* true)

(defn pots->set [s-pots]
  (->> (seq s-pots)
       (map-indexed vector)
       (filter (comp (partial = \#)
                     second))
       (map first)
       set))

(defn parse-rule [s-rule]
  (let [rule (re-find #"[.#]{5}" s-rule)
        live (->> (pots->set rule) (map #(- % 2)) set)
        dead (difference #{-2 -1 0 1 2} live)]
    [live dead]))

(defn parse-input [[s-state _ & s-rules]]
  {:state (-> (vec s-state) (subvec 15) pots->set)
   :rules (mapv parse-rule (filter (comp (partial = \#) last) s-rules))})

(defn matches-pots? [state live dead pot-index]
  (let [live (map (partial + pot-index) live)
        dead (map (partial + pot-index) dead)]
    (and (every? #(contains? state %) live)
         (every? #(not (contains? state %)) dead))))

(defn effect [[live dead] state new-state pot-index]
  (if (or
        (and (contains? live 0)
             (contains? state pot-index)
             (matches-pots? state live dead pot-index))
        (and (contains? dead 0)
             (not (contains? state pot-index))
             (matches-pots? state live dead pot-index)))
    (conj new-state pot-index)
    new-state))

; (defn effect-all [state new-state rule]
;   (let [[lowest highest] ((juxt (partial apply min)
;                                 (partial apply max)) state)
;         pot-indexes (range (- lowest 2) (+ highest 3))]
;     (reduce (partial effect rule state) new-state pot-indexes)))

(defn one-generation [rules state]
  (let [[lowest highest] ((juxt (partial apply min)
                                (partial apply max)) state)
        pot-indexes (range (- lowest 2) (+ highest 3))
        effect-all #(reduce (partial effect %2 state) %1 pot-indexes) ]
    (reduce effect-all #{} rules)))

; (defn solve1 [^long generation data]
;   (let [{:keys [state rules]} (parse-input data)
;         gens (iterate (partial one-generation rules) state)]
;     (->> (drop generation gens)
;          (take 1)
;          first
;          (reduce +))))

(defn solve1 [^long generation data]
  (let [{:keys [state rules]} (parse-input data)]
    (->> (reduce (fn [acc _] (one-generation rules acc))
                 state
                 (range generation))
         (reduce +))))
