(ns aoc2019.13
  (:require [aoc2019.intcode :as intcode]
            [clojure.string :as string]
            [clojure.core.async :refer [chan go-loop <! <!! alts! put!]]))

(defn solve1 [data]
  (let [out-ch (chan)
        halt-ch (chan)]
    (intcode/process data (chan) out-ch halt-ch)
    (->> (<!! (go-loop [tiles {}]
                (let [[x] (alts! [halt-ch out-ch])]
                  (if (number? x)
                    (let [y (<! out-ch)
                          id (<! out-ch)]
                      (recur (assoc tiles [x y] id)))
                    tiles))))
         (vals)
         (filter #{2})
         (count))))

(comment
  (defn print-tiles [tiles]
    (let [width (inc (reduce max (map first (keys tiles))))
          height (inc (reduce max (map second (keys tiles))))]
      (->> (for [y (range height)
                 x (range width)]
             (case (get tiles [x y])
               0 \.  1 \X 2 \# 3 \= 4 \o))
           (partition width)
           (map string/join)
           (string/join \newline)
           (println)))))

(defn solve2 [data]
  (let [in-ch (chan)
        out-ch (chan)
        halt-ch (chan)]
    (intcode/process (str "2" (subs data 1)) in-ch out-ch halt-ch)
    (<!! (go-loop [tiles {}
                   score 0
                   ball-x 0
                   paddle-x 0]
           (let [[x] (alts! [halt-ch out-ch])]
             (if (number? x)
               (let [y (<! out-ch)
                     v (<! out-ch)
                     score? (and (= x -1) (= y 0))
                     tiles' (if score? tiles (assoc tiles [x y] v))
                     score' (if score? v score)
                     ball-x' (if (and (not score?) (= v 4)) x ball-x)
                     paddle-x' (if (and (not score?) (= v 3)) x paddle-x)]
                 (when (not= ball-x ball-x')
                   (cond
                     (> ball-x' paddle-x') (put! in-ch 1)
                     (< ball-x' paddle-x') (put! in-ch -1)
                     :else (put! in-ch 0)))
                 (recur tiles' score' ball-x' paddle-x'))
               score))))))
