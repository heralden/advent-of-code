(ns aoc2018.25
  (:require [clojure.set]))

(defn read-point [s]
  (mapv read-string (re-seq #"-?\d+" s)))

(defn distance [p1 p2]
  (->> (map - p1 p2)
       (map #(Math/abs %))
       (reduce +)))

(defn constellation? [p1 p2]
  (let [dist (distance p1 p2)]
    (<= dist 3)))

(defn intersects? [constellation s]
  (not (empty? (clojure.set/intersection constellation s))))

(defn process-points [ps setsv p]
  (let [constellation (set (filter (partial constellation? p) ps))
        intersecting (group-by (partial intersects? constellation)
                               setsv)
        intersects (intersecting true)
        disjoints (intersecting false)]
    (if (empty? intersects)
      (conj disjoints constellation)
      (conj disjoints
            (apply clojure.set/union constellation intersects)))))

(defn solve1 [raw-points]
  (let [points (map read-point raw-points)]
    (->> (reduce (partial process-points points) [] points)
         count)))
