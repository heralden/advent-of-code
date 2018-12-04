(ns aoc2018.04)

(defn read-record [s]
  (let [[minute text]
        (rest (re-find #"\[\d+-\d+-\d+ \d+:(\d+)\] (.*)" s))
        id
        (second (re-matches #"Guard #(\d+) begins shift" text)) ]
    {:minute (Integer/parseInt minute)
     :id (if (some? id) (Integer/parseInt id) nil)
     :action (cond
               (some? id) :begin
               (= text "falls asleep") :sleep
               (= text "wakes up") :wake)}))

(defn sleeping-minutes [records]
  (let [last-id (atom nil)]
    (reduce (fn [acc {:keys [minute id action]}]
              (case action
                :begin (do (reset! last-id id)
                         acc)
                :sleep (update acc @last-id #(conj (or % []) minute))
                :wake  (update acc
                               @last-id
                               #(apply conj % (-> % peek inc (range minute))))))
            {}
            records)))

(defn mode-pair [v]
  (apply max-key val (frequencies v)))

(defn solve1 [raw-records]
  (let [records (map read-record (sort raw-records))
        slept-ids (sleeping-minutes records)
        sleepiest-id (key (apply max-key (comp count val) slept-ids))
        sleepiest-minute (key (mode-pair (get slept-ids sleepiest-id)))]
    (* sleepiest-id sleepiest-minute)))

(defn solve2 [raw-records]
  (let [records (map read-record (sort raw-records))
        slept-ids (sleeping-minutes records)
        [id minute _] (reduce-kv (fn [[gk gm gc] k v]
                                   (let [[m c] (mode-pair v)]
                                     (if (> c gc)
                                       [k m c]
                                       [gk gm gc])))
                                 [0 0 0]
                                 slept-ids)]
    (* id minute)))
