(ns aoc2018.09)

;; WIP

(defn read-sentence [s]
  (->> (re-matches #"(\d+) players; last marble is worth (\d+) points" s)
       rest
       (mapv read-string)))

(defn conj-nth [v i x]
  (into (conj (subvec v 0 i) x)
        (subvec v i)))

(defn rem-nth [v i]
  (into (subvec v 0 i)
        (subvec v (inc i))))

(defn marble->player [players marble]
  (rem marble players))

(defn next-index [game-length index]
  (if (= game-length index)
    1
    (+ index 2)))

(defn solve1 [s]
  (let [[players last-point] (read-sentence s)]
    (loop [game [0 2 1 3], marble 4, index 1, points (vec (repeat players 0))]
      ; (println game marble index points) ; DEBUG
      (assert (< marble (* last-point 5)) "TOO LONG") ; DEBUG
      (if (zero? (rem marble 23))
        (let [player (rem marble players)
              index-extra (mod (- index 9) (count game))
              extra (nth game index-extra)
              point (+ marble extra)
              new-points (update points player + point)]
          (if (= point last-point)
            ; [(rem-nth game index-extra) (inc marble) (+ index-extra 2) new-points]
            (reduce max new-points)
            (recur (rem-nth game index-extra)
                   (inc marble)
                   (next-index (count game) index-extra)
                   new-points)))
        (let [new-game (conj-nth game index marble)]
          (recur new-game
                 (inc marble)
                 (next-index (count game) index)
                 points))))))
