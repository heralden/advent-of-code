(ns aoc2018.03
  (:require [clojure.set :as s]))

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
    (reduce (fn [area index]
              (update area
                      index
                      #(if (= % 0) id \X)))
            area
            indices)))

(defn greatest-xy [claims]
  (reduce (fn [[gx gy] {:keys [x y w h]}]
            [(max gx (+ x w))
             (max gy (+ y h))])
          [0 0]
          claims))

(defn init-area [claims]
  (let [[gx gy] (greatest-xy claims)]
    (with-meta (vec (repeat (* gx gy) 0))
               {:width gx})))

(defn solve1 [claim-strs]
  (let [claims (map read-claim claim-strs)
        area (init-area claims)]
    (->> (reduce place-claim area claims)
         (filter #(= % \X))
         count)))

(defn overlapping-claim-ids [[ids area] {:keys [id x y w h]}]
  (let [indices (dims->indices area x y w h)]
    (reduce (fn [[overlaps area] index]
              (let [tile (get area index)]
                (if (= tile 0)
                  [overlaps (assoc area index id)]
                  [(conj overlaps tile id)
                   (assoc area index id)])))
            [ids area]
            indices)))

(defn solve2 [claim-strs]
  (let [claims (map read-claim claim-strs)
        area (init-area claims)
        all-ids (into #{} (map :id claims))
        overlapping-ids (first (reduce overlapping-claim-ids
                                       [#{} area]
                                       claims)) ]
    (first (s/difference all-ids overlapping-ids))))
