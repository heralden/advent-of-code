(ns aoc2018.16
  (:require [clojure.set :refer [intersection difference]]))

;;;; Definitions of instructions for register `rs`.
;; rs => [0 0 0 0]

(defn fn-r [f]
  "Returns function that calls f with registers in1 and in2."
  (fn [rs in1 in2 out]
    (assoc rs out (f (get rs in1)
                     (get rs in2)))))

(defn fn-i [f]
  "Returns function that calls f with register in1 and value in2."
  (fn [rs in1 in2 out]
    (assoc rs out (f (get rs in1) in2))))

(defn fn-ir [f]
  "Returns function that calls f with value in1 and register in2."
  (fn [rs in1 in2 out]
    (assoc rs out (f in1 (get rs in2)))))

(defn test-bit [pred? in1 in2]
  "Helper function that returns 1 or 0 for pred."
  (if (pred? in1 in2) 1 0))

;; Addition
(def addr (fn-r +))
(def addi (fn-i +))
;; Multiplication
(def mulr (fn-r *))
(def muli (fn-i *))
;; Bitwise AND
(def banr (fn-r bit-and))
(def bani (fn-i bit-and))
;; Bitwise OR
(def borr (fn-r bit-or))
(def bori (fn-i bit-or))
;; Assignment
(defn setr [rs in1 _in2 out]
  (assoc rs out (get rs in1)))
(defn seti [rs in1 _in2 out]
  (assoc rs out in1))

;; Greater-than testing
(def gtir (fn-ir (partial test-bit >)))
(def gtri (fn-i (partial test-bit >)))
(def gtrr (fn-r (partial test-bit >)))

;; Equality testing
(def eqir (fn-ir (partial test-bit =)))
(def eqri (fn-i (partial test-bit =)))
(def eqrr (fn-r (partial test-bit =)))

(def opcodes [addr addi mulr muli banr bani borr bori
              setr seti gtir gtri gtrr eqir eqri eqrr])

;;;; Solution

(defn read-sample [sample]
  (mapv (comp (partial mapv read-string)
              (partial re-seq #"\d+"))
       sample))

(defn parse-samples [raw-samples]
  (let [samples (partition 3 (filter #(not (empty? %))
                                     raw-samples))]
    (map read-sample samples)))

(defn correct-op? [[rs1 inst rs2] op]
  (let [op-rs (apply op rs1 (rest inst))]
    (= op-rs rs2)))

(defn valid-ops [sample]
  (->> (map (partial correct-op? sample) opcodes)
       (map-indexed vector)
       (filter (comp true? second))
       (map first)))

(defn solve1 [raw-samples]
  (let [samples (parse-samples raw-samples)]
    (->> (map valid-ops samples)
         (map count)
         (filter #(>= % 3))
         count)))

(defn read-insts [raw-insts]
  (->> (re-seq #"\d+" raw-insts)
       (mapv read-string)))

(defn map-values [f m]
  (reduce-kv (fn [m k v]
               (assoc m k (f v))) {} m))

(defn intersect-ops [v]
  (->> (map second v)
       (map set)
       (apply intersection)))

(defn unique-ops [[opm taken] [k s]]
  (let [new-s (difference s taken)
        op (first new-s)]
    (cond
      (= (count opm) (count opcodes)) (reduced opm)
      (= 1 (count new-s)) [(assoc opm k op)
                           (conj taken op)]
      :default [opm taken])))

(defn run-inst [op-map rs [op & data]]
  (apply (op-map op) rs data))

(defn solve2 [raw-samples raw-program]
  (let [samples (parse-samples raw-samples)
        get-opcode #(get-in % [1 0])
        op-map (->> (map (juxt get-opcode valid-ops) samples)
                    (group-by first)
                    (map-values intersect-ops)
                    cycle
                    (reduce unique-ops [{} #{}])
                    (map-values #(get opcodes %)))
        insts (map read-insts raw-program)]
    (first (reduce (partial run-inst op-map) [0 0 0 0] insts))))
