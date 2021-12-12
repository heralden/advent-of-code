(ns aoc2021.10
  (:require [clojure.string :as str]))

(def opens [\( \[ \{ \<])
(def closes [\) \] \} \>])
(def open->close (zipmap opens closes))
(def close->open (zipmap closes opens))

(defn validate-line [line]
  (reduce (fn [stack c]
            (cond
              (open->close c) (conj stack c)
              (= (close->open c) (peek stack)) (pop stack)
              :else (reduced c)))
          [] line))

(def scoring1 {\) 3 \] 57 \} 1197 \> 25137})

(defn solve1 [data]
  (->> (str/split-lines data)
       (keep #(let [ret (validate-line %)]
                (when (char? ret) ret)))
       (map scoring1)
       (reduce +)))

(def scoring2 {\( 1 \[ 2 \{ 3 \< 4})

(defn score2 [chars]
  (reduce #(+ (scoring2 %2) (* 5 %1)) 0 (rseq chars)))

(defn solve2 [data]
  (let [scores (->> (str/split-lines data)
                    (keep #(let [ret (validate-line %)]
                             (when (coll? ret) ret)))
                    (map score2))]
    (nth (sort scores) (-> scores count (/ 2) Math/floor))))
