(ns aoc2018.03)

(defn read-claim [claim]
  (let [matcher (re-matcher #"\d+" claim)
        match #(-> matcher re-find read-string)]
    {:id (match)
     :x  (match)
     :y  (match)
     :w  (match)
     :h  (match)}))

(defn xy->i [area [x y]]
  (let [width (-> area meta :width)]
    (+ x (* y width))))

(defn dims->indices [area x y w h]
  (let [xys (for [x (range x (+ x w))
                  y (range y (+ y h))]
               [x y])]
    (map (partial xy->i area) xys)))

(defn place-claim [area {:keys [id x y w h]}]
  (let [indices (dims->indices area x y w h)]
    (reduce #(assoc %1 %2 id) area indices)))

(defn solve1 [claims])
