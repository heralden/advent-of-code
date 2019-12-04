(ns aoc2019.04)

(defn num->seq [num]
  (->> (str num)
       (re-seq #"\d")
       (map #(Integer/parseInt %))))

(defn not-decreasing? [number]
  (boolean (reduce (fn [prev n]
                     (if (<= prev n)
                       n
                       (reduced false)))
                   0
                   (num->seq number))))

(defn valid1? [number]
  (and (let [s (num->seq number)]
         (not= (dedupe s) s))
       (not-decreasing? number)))

(defn solve1 [interval]
  (->> (apply range interval)
       (filter valid1?)
       count))

(defn two-adjacent? [number]
  (boolean (->> (num->seq number)
                (partition-by identity)
                (some #(= (count %) 2)))))

(defn valid2? [number]
  (and (not-decreasing? number)
       (two-adjacent? number)))

(defn solve2 [interval]
  (->> (apply range interval)
       (filter valid2?)
       count))
