(ns aoc2019.07
  (:require [clojure.core.logic :as l]
            [clojure.core.logic.fd :as fd]
            [clojure.string :as string]))

(defn phase-settings [low high]
  (l/run* [a b c d e]
    (l/everyg #(fd/in % (fd/interval low high)) [a b c d e])
    (l/distincto [a b c d e])))

(defn read-intcodes [intcodes]
  (mapv read-string (string/split intcodes #",")))

(defn process [phase-in signal-in [program pos in] _]
  (let [[code r1 r2 rout] (subvec program pos)
        v1 (get program r1)
        v2 (get program r2)
        [m2 m1 _ instr] (->> (format "%04d" code)
                             (map (comp read-string str)))
        f1 (if (= m1 1) r1 v1)
        f2 (if (= m2 1) r2 v2)]
    (case instr
      1 [(assoc program rout (+ f1 f2)) (+ pos 4) in]
      2 [(assoc program rout (* f1 f2)) (+ pos 4) in]
      3 [(assoc program r1 (case in
                             :phase phase-in
                             :signal signal-in)) (+ pos 2) :signal]
      4 (reduced f1)
      5 [program (if (not (zero? f1)) f2 (+ pos 3)) in]
      6 [program (if (zero? f1) f2 (+ pos 3)) in]
      7 [(assoc program rout (if (< f1 f2) 1 0)) (+ pos 4) in]
      8 [(assoc program rout (if (= f1 f2) 1 0)) (+ pos 4) in])))

(defn run [program signal-in phase-in]
  (reduce (partial process phase-in signal-in) [program 0 :phase] (range)))

(defn run-amps [program phase-setting]
  (reduce #(run program %1 %2) 0 phase-setting))

(defn process2 [signal-in [program pos in phase :as amp] _]
  (let [[code r1 r2 rout] (subvec program pos)
        v1 (get program r1)
        v2 (get program r2)
        [m2 m1 _ instr] (->> (format "%04d" code)
                             (map (comp read-string str)))
        f1 (if (= m1 1) r1 v1)
        f2 (if (= m2 1) r2 v2)]
    (case instr
      1 [(assoc program rout (+ f1 f2)) (+ pos 4) in phase]
      2 [(assoc program rout (* f1 f2)) (+ pos 4) in phase]
      3 [(assoc program r1 (case in
                             :init 0
                             :phase phase
                             :signal signal-in))
         (+ pos 2) (case in :init :phase :signal) phase]
      4 (reduced [f1 [program (+ pos 2) in phase] false])
      5 [program (if (not (zero? f1)) f2 (+ pos 3)) in phase]
      6 [program (if (zero? f1) f2 (+ pos 3)) in phase]
      7 [(assoc program rout (if (< f1 f2) 1 0)) (+ pos 4) in phase]
      8 [(assoc program rout (if (= f1 f2) 1 0)) (+ pos 4) in phase]
      (reduced [signal-in amp true]))))

(defn run2 [[signal amps] index]
  (let [[new-signal new-amp done?]
        (reduce (partial process2 signal) (get amps index) (range))]
    (if (and done? (= index 4))
      (reduced new-signal)
      [new-signal (assoc amps index new-amp)])))

(defn run-amps2 [program phase-setting]
  (let [amps (vec (map-indexed (fn [i phase]
                                 [program 0 (if (zero? i) :init :phase) phase])
                               phase-setting))]
    (reduce run2 [0 amps] (cycle (range (count amps))))))

(defn solve1 [intcodes]
  (let [program (read-intcodes intcodes)]
    (transduce (map (partial run-amps program)) max 0 (phase-settings 0 4))))

(comment
  (def data (->> "2019/07" clojure.java.io/resource slurp))
  (def intcodes "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10")
  (def program (read-intcodes intcodes)))

(defn solve2 [intcodes]
  (let [program (read-intcodes intcodes)]
    (transduce (map (partial run-amps2 program)) max 0 (phase-settings 5 9))))
