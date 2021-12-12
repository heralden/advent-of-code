(ns aoc2021.12
  (:require [clojure.string :as str]))

;; Simpler version sufficient for part 1.
#_(defn explore [cave-system visited-small-caves path]
    (let [current-cave (last path)
          next-caves (remove visited-small-caves (cave-system current-cave))]
      (cond
        (= "end" current-cave) [path]
        (empty? next-caves) [path]
        :else (for [next next-caves
                    new-path (explore cave-system
                                      (cond-> visited-small-caves
                                        (Character/isLowerCase (first next)) (conj next))
                                      (conj path next))]
                new-path))))

(defn explore [cave-system visited-small-caves visited-twice path]
  (let [current-cave (last path)
        next-caves (if (nil? visited-twice)
                     (remove #{"start"} (cave-system current-cave))
                     (remove visited-small-caves (cave-system current-cave)))]
    (cond
      (= "end" current-cave) [path]
      (empty? next-caves) [path]
      :else (for [next next-caves
                  new-path (explore cave-system
                                    (cond-> visited-small-caves
                                       (Character/isLowerCase (first next)) (conj next))
                                    (if (and (nil? visited-twice)
                                             (visited-small-caves next))
                                      next
                                      visited-twice)
                                    (conj path next))]
              new-path))))

(defn solve [init-twice data]
  (let [cave-system (->> (map #(str/split % #"-") (str/split-lines data))
                         (reduce (fn [cave-system [a b]]
                                   (-> cave-system
                                       (update a (fnil conj #{}) b)
                                       (update b (fnil conj #{}) a))) {}))]
    (->> (explore cave-system #{"start"} init-twice ["start"])
         (filter #(= "end" (last %)))
         (count))))

(def solve1 (partial solve "start"))
(def solve2 (partial solve nil))
