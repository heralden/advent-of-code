(ns aoc2021.05)

(defn drange [start end]
  (if (< end start)
    (range start (dec end) -1)
    (range start (inc end))))

(defn draw-line [coords [x1 y1 x2 y2]]
  (let [{:keys [diagonal?]} (meta coords)]
    (reduce
      #(update %1 %2 (fnil inc 0))
      coords
      (cond
        (= x1 x2) (map vector (repeat x1) (drange y1 y2))
        (= y1 y2) (map vector (drange x1 x2) (repeat y1))
        diagonal? (map vector (drange x1 x2) (drange y1 y2))))))

(defn overlapping [coords data]
  (let [line-segments (->> (re-seq #"\d+" data)
                           (map #(Integer/parseInt %))
                           (partition 4))]
    (->> (reduce draw-line coords line-segments)
         (vals)
         (filter #(> % 1))
         (count))))

(def solve1 (partial overlapping {}))
(def solve2 (partial overlapping (with-meta {} {:diagonal? true})))
