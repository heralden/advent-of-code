(ns aoc2019.08
  (:require [clojure.string :as string]
            [clojure.pprint :refer [pprint]]))

(defn read-image [data]
  (let [clean-data (first (string/split-lines data))]
    (map #(Integer/parseInt %) (string/split clean-data #""))))

(defn solve1 [data width height]
  (let [layers (partition (* width height) (read-image data))]
    (->> (apply min-key #(count (filter zero? %)) layers)
         ((juxt #(count (filter #{1} %))
                #(count (filter #{2} %))))
         (apply *))))

(defn merge-pixels [& pixels]
  (some #{0 1} pixels))

(defn solve2 [data width height]
  (let [layers (partition (* width height) (read-image data))
        image  (partition width (apply map merge-pixels layers))]
    (with-out-str (pprint image))))

