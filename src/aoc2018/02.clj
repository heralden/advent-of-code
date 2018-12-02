(ns aoc2018.02)

(defn solve1 [ids]
  (let [contains-fun (juxt #(contains? % 2) #(contains? % 3))]
    (->> (map (comp contains-fun set vals frequencies) ids)
         (reduce (partial map (fn [n bool] (if bool (inc n) n)))
                 [0 0])
         (reduce *))))

(defn count-diffs [s1 s2]
  (let [char-pairs (map vector s1 s2)
        matching (map (partial apply =) char-pairs)]
    (get (frequencies matching) false)))

(defn diff-ids [ids id]
  (some #(when (= 1 (count-diffs % id)) [% id]) ids))

(defn solve2 [ids]
  (reduce (fn [ids id]
            (if-let [match (diff-ids ids id)]
              (reduced match)
              (conj ids id)))
          []
          ids))
