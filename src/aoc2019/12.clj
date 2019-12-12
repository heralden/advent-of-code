(ns aoc2019.12)

(defn read-moons [data]
  (->> (re-seq #"-?\d+" data)
       (map #(Integer/parseInt %))
       (partition 3)
       (map vec)
       (into [])))

(defn axis [v1 a1 a2]
  (cond
    (= a1 a2) v1
    (> a1 a2) (dec v1)
    (< a1 a2) (inc v1)))

(defn velocity [[x1 y1 z1 :as _m1] [x0 y0 z0 :as _v1] [x2 y2 z2 :as _m2]]
  [(axis x0 x1 x2) (axis y0 y1 y2) (axis z0 z1 z2)])

(defn remvec [v i]
  (vec (concat (subvec v 0 i)
               (subvec v (inc i)))))

(defn apply-velocity [moons i vel]
  (reduce (partial velocity (get moons i)) vel (remvec moons i)))

(defn gravity [[x0 y0 z0 :as _v1] [x1 y1 z1 :as _m1]]
  [(+ x0 x1) (+ y0 y1) (+ z0 z1)])

(defn simulate-step [[moons vels] _]
  (let [vels' (vec (map-indexed (partial apply-velocity moons) vels))
        moons' (vec (map gravity vels' moons))]
    [moons' vels']))

(defn simulate [data steps]
  (let [moons (read-moons data)
        vels (vec (repeat (count moons) (vec (repeat 3 0))))]
    (reduce simulate-step [moons vels] (range steps))))

(defn abs [x] (Math/abs x))

(defn energy [[moons vels]]
  (reduce +
          (map *
               (map (comp #(reduce + %) #(map abs %)) moons)
               (map (comp #(reduce + %) #(map abs %)) vels))))

(defn solve1 [steps data]
  (->> (simulate data steps)
       (energy)))

(defn simulate-eternity-step [[moons vels :as state] step]
  (let [vels' (vec (map-indexed (partial apply-velocity moons) vels))
        moons' (vec (map gravity vels' moons))
        state' [moons' vels']]
    (if (= state' state)
      (reduced (inc step))
      state')))

(defn simulate-eternity [data]
  (let [moons (read-moons data)
        vels (vec (repeat (count moons) (vec (repeat 3 0))))]
    (reduce simulate-eternity-step [moons vels] (range))))

(defn solve2 [data]
  (simulate-eternity data))
