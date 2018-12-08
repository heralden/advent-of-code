(ns aoc2018.07
  (:require [clojure.core.logic :refer [run* fresh everyg !=]]
            [clojure.core.logic.pldb :refer [db-rel with-db db]]
            [clojure.set :as s]))

(db-rel before x y)

(defn read-fact [f]
  (let [x (symbol (subs f 5 6))
        y (symbol (subs f 36 37))]
    [before x y]))

(defn satisfied? [facts s lvar]
  (every? (partial contains? s)
          (with-db facts (run* [q] (before q lvar)))))

(defn find-steps [facts s lvar]
  (->> (with-db facts (run* [q] (before lvar q) (everyg (partial != q) s)))
       (filter (partial satisfied? facts s))))

(defn next-steps [facts s]
  (->> (map (partial find-steps facts s) s)
       flatten
       (filter some?)))

(defn first-steps [facts s]
  (let [set1 (into #{} (with-db facts (run* [q] (fresh [x] (before q x)))))
        set2 (into #{} (with-db facts (run* [q] (fresh [x] (before x q)))))]
    (remove s (s/difference set1 set2))))

(defn sorted-first [coll]
  (first (sort coll)))

(defn solve1 [raw-facts]
  (let [facts (apply db (map read-fact raw-facts))
        fvar (sorted-first (first-steps facts #{}))]
    (loop [v [fvar]]
      (let [step (->> (concat (next-steps facts (set v))
                              (first-steps facts (set v)))
                      sorted-first)]
        (if (some? step)
          (recur (conj v step))
          (apply str v))))))

(defn sym->sec [base sym]
  (-> sym str seq first char int (- 64) (+ base)))

(defn init-workers [amount init]
  (vec (cons init (repeat (dec amount) 0))))

(defn lowest-pos-val [v]
  (->> (filter pos? v)
       (reduce min)))

(defn finish-work [workers]
  (let [sec (lowest-pos-val workers)]
    (mapv #(if (pos? %) (- % sec) 0) workers)))

(defn min-index [v]
  (->> (map-indexed vector v)
       (apply min-key second)
       first))

(defn assign-work [workers sec]
  (let [idle-i (min-index workers)]
    (assoc workers idle-i sec)))

(defn busy? [workers]
  (> (reduce min workers) 0))

(defn solve2 [worker-count step-sec raw-facts]
  (let [facts (apply db (map read-fact raw-facts))
        fvar (sorted-first (first-steps facts #{}))
        fworkers (init-workers worker-count (sym->sec step-sec fvar))
        fqueue (conj clojure.lang.PersistentQueue/EMPTY fvar)]
    (loop [v [], curr 0, workers fworkers, queue fqueue]
      (let [steps (remove (set queue)
                          (concat (next-steps facts (set v))
                                  (first-steps facts (set v))))]
        ;; (println curr workers v (vec queue) steps)
        (cond
          (every? empty? [steps queue]) curr
          (or (empty? steps)
              (busy? workers)) (recur (conj v (peek queue))
                                      (+ curr (lowest-pos-val workers))
                                      (finish-work workers)
                                      (pop queue))
          :default (let [step (sorted-first steps)
                         sec (sym->sec step-sec step)]
                     (recur v curr (assign-work workers sec) (conj queue step))))))))

;;;; Notes on `solve2`
;; Some issues that may occur on different data:
;; - Handle case where two workers complete at the same time
;; - Queue doesn't deplete in the correct order
;; Possible performance improvements:
;; - Allow assigning multiple workers at the same time
;;   (but be sure to remove duplicates in `steps`)
