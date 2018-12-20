(ns aoc2018.19)

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

(defn read-inst [[opcode & data]]
  (apply vector (keyword opcode) (map read-string data)))

(defn parse-input [[raw-ptr & raw-data]]
  (let [ptr (->> (re-matches #"#ip (\d)" raw-ptr)
                 second
                 read-string)
        insts (map #(->> (re-matches #"(\w+) (\d+) (\d+) (\d+)" %)
                         rest
                         read-inst)
                   raw-data)]
    (apply vector ptr insts)))

(defn get-op [opcode]
  (case opcode
    :addr (fn-r +)
    :addi (fn-i +)
    :mulr (fn-r *)
    :muli (fn-i *)
    :banr (fn-r bit-and)
    :bani (fn-i bit-and)
    :borr (fn-r bit-or)
    :bori (fn-i bit-or)
    :setr (fn [rs in1 _in2 out]
            (assoc rs out (get rs in1)))
    :seti (fn [rs in1 _in2 out]
            (assoc rs out in1))
    :gtir (fn-ir (partial test-bit >))
    :gtri (fn-i (partial test-bit >))
    :gtrr (fn-r (partial test-bit >))
    :eqir (fn-ir (partial test-bit =))
    :eqri (fn-i (partial test-bit =))
    :eqrr (fn-r (partial test-bit =))
    nil))

(defn run-insts [ptr insts rs _]
  (let [[opcode & data] (get insts (rs ptr))
        op (get-op opcode)]
    (if (some? op)
      (-> (apply op rs data)
          (update ptr inc))
      (reduced rs))))

(defn run-program [[ptr & insts] rs]
  (reduce (partial run-insts ptr (vec insts)) rs (range)))

(defn solve1 [raw-input]
  (let [input (parse-input raw-input)
        init-rs (vec (repeat 6 0))]
    (first (run-program input init-rs))))

(defn solve2 [raw-input]
  (let [input (parse-input raw-input)
        init-rs [1 0 0 0 0 0]]
    (first (run-program input init-rs))))
