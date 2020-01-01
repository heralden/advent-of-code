(ns aoc2019.23
  (:require [aoc2019.intcode :as intcode]
            [clojure.core.async :refer [go-loop chan >! <! <!! >!! alts! alt! timeout]]))

(defn network [program & {:keys [term-nat?]}]
  (let [computers (mapv (fn [addr]
                          (let [in-ch (chan)
                                out-ch (chan 1 (map #(vector % addr)))
                                halt-ch (chan)
                                ctrl-ch (chan)
                                recv-ch (chan)]
                            {:in in-ch :out out-ch :halt halt-ch
                             :ctrl ctrl-ch :recv recv-ch :addr addr}))
                        (range 50))
        nat {:send (chan) :recv (chan)}]
    ;; NAT
    (go-loop [packet [0 0]]
      ;; If initial packet is nil, a "can't put nil on channel" exception will
      ;; be thrown even though no takes are done on the channel.
      (recur (alt!
               [[(:send nat) packet]] packet
               (:recv nat) ([packet'] packet'))))
    ;; NIC
    (doseq [{:keys [in out halt ctrl recv addr]} computers]
      (intcode/process program in out halt ctrl)
      (>!! in addr)
      (go-loop [q clojure.lang.PersistentQueue/EMPTY]
        (let [return (alt!
                       [[in (or (first (peek q)) -1)]] :sent
                       recv ([v] (conj q v)))]
          (recur
           (cond
             ;; Consume packet queue.
             (and (= return :sent) (peek q))
             (do (>! in (second (peek q)))
                 (pop q))
             ;; Packet queue is empty, provided -1.
             (= return :sent)
             q
             ;; Received packet.
             :else
             return)))))
    (<!!
     ;; LAN
     (go-loop [monitor-packet [0 0]]
       (let [[[to from :as value] _]
             (alts! (conj (mapv :out computers) (timeout 200)))]
         (if (some? value)
           (let [[x _] (<! (get-in computers [from :out]))
                 [y _] (<! (get-in computers [from :out]))]
             (>! (if (= to 255)
                   (get nat :recv)
                   (get-in computers [to :recv]))
                 [x y])
             (if (and term-nat? (= to 255))
               y ;; Part 1.
               (recur monitor-packet)))
           (let [nat-packet (<! (:send nat))]
             (if (= nat-packet monitor-packet)
               (second nat-packet) ;; Part 2.
               (do (>! (get-in computers [0 :recv]) nat-packet)
                   (recur nat-packet))))))))))

(defn solve1 [program]
  (network program :term-nat? true))

(defn solve2 [program]
  (network program))
