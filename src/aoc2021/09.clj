(ns aoc2021.09
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-heights [data]
  (let [width (-> (str/split-lines data) first count)
        heights (mapv #(Integer/parseInt %) (re-seq #"\d" data))]
    (with-meta heights {:width width})))

(defn neighbours [heights index]
  (let [{:keys [width]} (meta heights)]
    (cond-> []
      (>= index width)                    (conj (- index width)) ; North
      (pos? (rem (inc index) width))      (conj (inc index))     ; East
      (< index (- (count heights) width)) (conj (+ index width)) ; South
      (pos? (rem index width))            (conj (dec index)))))  ; West

(defn lows [heights]
  (for [[index height] (map-indexed vector heights)
        :when (every? #(< height (get heights %)) (neighbours heights index))]
    index))

(defn solve1 [data]
  (let [heights (parse-heights data)]
    (->> (map #(get heights %) (lows heights))
         (map inc) (reduce +))))

(defn expand-basin [heights basin index]
  (let [height (get heights index)
        nlows (keep (fn [nindex]
                      (let [nheight (get heights nindex)]
                        (when (and (< nheight 9) (> nheight height))
                          nindex)))
                    (neighbours heights index))]
    (if (empty? nlows)
      [basin]
      (for [low nlows
            nbasin (expand-basin heights (conj basin low) low)]
        nbasin))))

(defn solve2 [data]
  (let [heights (parse-heights data)]
    (->> (lows heights)
         (map #(expand-basin heights #{%} %))
         (map #(apply set/union %))
         (map count) (sort >) (take 3) (reduce *))))
