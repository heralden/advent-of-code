(ns aoc2021.15
  (:require [clojure.string :as str]
            [clojure.data.priority-map :refer [priority-map]]))

(defn neighbours [area index]
  (let [{:keys [width height]} (meta area)]
    (cond-> []
      (>= index width)                     (conj (- index width)) ; North
      (pos? (rem (inc index) width))       (conj (inc index))     ; East
      (< index (- (* width height) width)) (conj (+ index width)) ; South
      (pos? (rem index width))             (conj (dec index)))))  ; West

(defn i->xy [area index]
  (let [{:keys [width]} (meta area)]
    [(rem index width) (int (/ index width))]))

(defn goal [area]
  (let [{:keys [width height]} (meta area)]
    (dec (* width height))))

(defn h [area index]
  (let [[x1 y1] (i->xy area index)
        [x2 y2] (i->xy area (goal area))]
    (int (Math/sqrt (+ (Math/pow (- x1 x2) 2)
                       (Math/pow (- y1 y2) 2))))))

(defn bound+ [a b]
  (let [n (rem (+ a b) 9)]
    (if (zero? n) 9 n)))

(defn xy->real-i [area [x y]]
  (let [{:keys [rwidth rheight]} (meta area)
        x (rem x rwidth)
        y (rem y rheight)]
    (+ x (* y rwidth))))

(defn d [area index]
  (let [{:keys [rwidth rheight]} (meta area)
        [x y] (i->xy area index)
        extra (+ (int (/ x rwidth)) (int (/ y rheight)))]
    (bound+ (get area (xy->real-i area [x y])) extra)))

(defn a* [area]
  (loop [openSet (priority-map 0 (h area 0))
         gScore {0 0}]
    (let [[current current-cost] (peek openSet)]
      (cond
        (empty? openSet) nil
        (= current (goal area)) current-cost
        :else (let [[openSet' gScore']
                    (reduce (fn [[o g] n]
                              (let [score (+ (get gScore current ##Inf) (d area n))]
                                (if (< score (get gScore n ##Inf))
                                  [(assoc o n (+ score (h area n))) (assoc g n score)]
                                  [o g])))
                            [(dissoc openSet current) gScore] (neighbours area current))]
                (recur openSet' gScore'))))))

(defn solve [multiplier data]
  (let [lines (str/split-lines data)
        width (-> lines first count)
        height (-> lines count)
        area (with-meta (mapv #(Integer/parseInt %) (re-seq #"\d" data))
                        {:width (* width multiplier)
                         :height (* height multiplier)
                         :rwidth width
                         :rheight height})]
    (a* area)))

(def solve1 (partial solve 1))
(def solve2 (partial solve 5))
