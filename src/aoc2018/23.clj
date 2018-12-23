(ns aoc2018.23)

(defn read-nanobot [s]
  (->> (re-seq #"-?\d+" s)
       (map read-string)
       (zipmap [:x :y :z :r])))

(defn distance [bot1 bot2]
  (let [get-coords (juxt :x :y :z)
        [x1 y1 z1] (get-coords bot1)
        [x2 y2 z2] (get-coords bot2)]
    (->> [(- x1 x2) (- y1 y2) (- z1 z2)]
         (map #(Math/abs %))
         (reduce +))))

(defn within-range? [{:keys [r]} dist]
  (<= dist r))

(defn solve1 [input]
  (let [nanobots (map read-nanobot input)
        gbot (apply max-key :r nanobots)]
    (->> nanobots
         (map (partial distance gbot))
         (filter (partial within-range? gbot))
         count)))

#_(defn bot->range [{:keys [x y z r] :as bot}]
  (assoc bot
         :gx (+ x r) :lx (- x r)
         :gy (+ y r) :ly (- y r)
         :gz (+ z r) :lz (- z r)))

#_(defn solve2 [input]
  (let [nanobots (map read-nanobot input)]
    ))
