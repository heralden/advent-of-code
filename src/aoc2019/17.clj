(ns aoc2019.17
  (:require [aoc2019.intcode :as intcode]
            [clojure.core.async :refer [chan <!! go-loop alts!]]
            [clojure.string :as string]))

(defn read-area [data]
  (let [out-ch (chan)
        halt-ch (chan)]
    (intcode/process data (chan) out-ch halt-ch)
    (<!! (go-loop [area []]
           (let [[tile] (alts! [out-ch halt-ch])]
             (if (number? tile)
               (recur (conj area (char tile)))
               area))))))

(defn solve1 [data]
  (let [area (mapv #(string/split % #"")
                   (-> (read-area data)
                       (string/join)
                       (string/split-lines)))]
    (->> (for [y (range (count area))
               x (range (count (first area)))
               :when (= "#" (get-in area [y x])
                            (get-in area [(inc y) x])
                            (get-in area [(dec y) x])
                            (get-in area [y (inc x)])
                            (get-in area [y (dec x)]))]
           (* x y))
         (reduce +))))

(defn solve2 [data])
