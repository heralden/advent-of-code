(ns aoc2018.06)

(defn read-point [raw-point]
  (->> (re-matches #"(\d+), (\d+)" raw-point)
       rest
       (mapv read-string)))

(defn extreme-points [points]
  (let [xs (map first points)
        ys (map second points)]
    {:min-x (apply min xs)
     :min-y (apply min ys)
     :max-x (apply max xs)
     :max-y (apply max ys)}))

(defn distance [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2))
     (Math/abs (- y1 y2))))

(defn closest-point [points coord]
  (let [[c1 c2] (->> (map distance points (repeat coord))
                     (map-indexed vector)
                     (sort-by second))]
    (if (= (second c1)
           (second c2))
      nil
      (nth points (first c1)))))

(defn solve1 [raw-points]
  (let [points (map read-point raw-points)
        {:keys [min-x min-y max-x max-y]} (extreme-points points)
        coords (for [x (range min-x max-x)
                     y (range min-y max-y)]
                    [x y])
        areas (pmap (partial closest-point points) coords)
        some-freqs (dissoc (frequencies areas) nil)]
    (second (apply max-key val some-freqs))))

(defn proximity? [points within-distance coord]
  (let [total-distance (->> (map distance points (repeat coord))
                            (reduce +))]
    (< total-distance within-distance)))

(defn solve2 [within raw-points]
  (let [points (map read-point raw-points)
        {:keys [min-x min-y max-x max-y]} (extreme-points points)
        coords (for [x (range min-x max-x)
                     y (range min-y max-y)]
                    [x y])]
    (count (filter (partial proximity? points within) coords))))
