(ns aoc2019.11
  (:require [aoc2019.intcode :as intcode]
            [clojure.core.async :refer [chan <! <!! go-loop alts!]]
            [clojure.math.numeric-tower :refer [abs]]))

(let [dirs [:up :right :down :left]]
  (defn rotate [dir sig]
    (let [sig' (if (zero? sig) -1 1)]
      (get dirs (mod (+ (.indexOf dirs dir) sig') (count dirs))))))

(defn move [dir [x y]]
  (case dir
    :up [x (inc y)]
    :down [x (dec y)]
    :left [(dec x) y]
    :right [(inc x) y]))

(defn paint [intcodes prepainted]
  (let [in-ch (chan)
        out-ch (chan)
        halt-ch (chan)]
    (intcode/process intcodes in-ch out-ch halt-ch)
    (go-loop [painted prepainted
              dir :up
              coord [0 0]]
      (let [[active?] (alts! [halt-ch [in-ch (get painted coord 0)]])]
        (if active?
          (let [painted' (assoc painted coord (<! out-ch))
                dir' (rotate dir (<! out-ch))
                coord' (move dir' coord)]
            (recur painted' dir' coord'))
          painted)))))

(defn solve1 [data]
  (count (<!! (paint data {}))))

(defn solve2 [data]
  (let [painting (<!! (paint data {[0 0] 1}))
        points   (reduce (fn [v [p w]]
                           (if (zero? w) v (conj v p)))
                         [] painting)
        [min-x min-y
         max-x max-y] (->> points
                           ((juxt #(transduce (map first) min Integer/MAX_VALUE %)
                                  #(transduce (map second) min Integer/MAX_VALUE %)
                                  #(transduce (map first) max Integer/MIN_VALUE %)
                                  #(transduce (map second) max Integer/MIN_VALUE %)))
                           (map abs))
        width  (inc (+ min-x max-x))
        height (inc (+ min-y max-y))
        points' (mapv (fn [[x y]] [(+ x min-x) (+ y min-y)]) points)]
    (->> (for [y (range height)
               x (range width)]
           (if (neg? (.indexOf points' [x y])) \. \#))
         (partition width))))
