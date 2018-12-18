(ns aoc2018.18)

(def ^:const open \.)
(def ^:const tree \|)
(def ^:const lumb \#)

(defn parse-area [raw-area]
  (with-meta (mapv vec raw-area)
             {:width (count (first raw-area))
              :height (count raw-area)}))

(defn adjacent-acres [area [x y]]
  (let [dirs [[(dec y) x]
              [(dec y) (inc x)]
              [y (inc x)]
              [(inc y) (inc x)]
              [(inc y) x]
              [(inc y) (dec x)]
              [y (dec x)]
              [(dec y) (dec x)]]]
    (->> (map #(get-in area %) dirs)
         (filter some?)
         frequencies
         (merge {open 0 tree 0 lumb 0}))))

(defn effect-acre [area new-area [x y]]
  (let [acre (get-in area [y x])
        env (adjacent-acres area [x y])]
    (cond
      (and (= open acre)
           (> (env tree) 2)) (assoc-in new-area [y x] tree)
      (and (= tree acre)
           (> (env lumb) 2)) (assoc-in new-area [y x] lumb)
      (and (= lumb acre)
           (> (env lumb) 0)
           (> (env tree) 0)) new-area
      (= lumb acre) (assoc-in new-area [y x] open)
      :default new-area)))

(defn effect-area [area _]
  (let [{:keys [width height]} (meta area)
        coords (for [y (range height)
                     x (range width)]
                 [x y])]
    (reduce (partial effect-acre area) area coords)))

(defn solve1 [raw-area minutes]
  (let [area (parse-area raw-area)
        res (->> (reduce effect-area area (range minutes))
                 flatten
                 frequencies
                 (merge {open 0 tree 0 lumb 0}))]
    (* (res tree) (res lumb))))
