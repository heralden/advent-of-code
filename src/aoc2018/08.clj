(ns aoc2018.08)

(defn read-nodes [raw-data]
  (map read-string (clojure.string/split raw-data #" ")))

(defn dec-head [l]
  (if (empty? l)
    l
    (conj (pop l) (dec (peek l)))))

(defn solve1 [raw-data]
  (loop [sum 0, childs '(), metas '(), nodes (read-nodes raw-data)]
    ;; (println sum childs metas)
    (cond
      (and (some? (peek childs))
           (zero? (peek childs)))
        (recur (+ sum (reduce + (take (peek metas) nodes)))
               (pop childs)
               (pop metas)
               (drop (peek metas) nodes))
      (empty? nodes) sum
      :default (let [[c m] (take 2 nodes)]
                 (recur sum
                        (conj (dec-head childs) c)
                        (conj metas m)
                        (drop 2 nodes))))))

;; Below is WIP

#_(defn zero-head [l]
  (if (empty? l)
    l
    (conj (pop l) 0)))

(defn add-up [mem sums]
  (let [add (reduce (fn [acc [k v]]
                      (if (every? some? [k v])
                        (concat acc
                                (map (comp (partial concat k)
                                           list)
                                     v))
                        acc))
                    '()
                    mem)]
    (reduce (fn [acc k] (+ acc (or (get sums k) 0))) 0 add)))

(defn safe-pop [l]
  (if (empty? l)
    l
    (pop l)))

(defn solve2 [raw-data]
  (loop [sums {}, childs '(), metas '(), nodes (read-nodes raw-data), mem {}]
    ; (println sums childs metas mem)
    (cond
      (and (some? (peek childs))
           (zero? (peek childs)))
        (recur (if (list? (get mem (safe-pop childs)))
                 sums
                 (assoc sums childs (reduce + (take (peek metas) nodes))))
               (pop childs)
               (pop metas)
               (drop (peek metas) nodes)
               (if (list? (get mem (safe-pop childs)))
                 (assoc mem (safe-pop childs) (take (peek metas) nodes))
                 mem))
      (empty? nodes) (add-up mem sums)
      :default (let [[c m] (take 2 nodes)]
                 (recur sums
                        (conj (dec-head childs) c)
                        (conj metas m)
                        (drop 2 nodes)
                        (if (pos? c)
                          (assoc mem (safe-pop childs) '())
                          mem))))))
