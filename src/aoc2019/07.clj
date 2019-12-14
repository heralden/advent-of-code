(ns aoc2019.07
  (:require [clojure.core.logic :as l]
            [clojure.core.logic.fd :as fd]
            [clojure.core.async :refer [chan <!! put! go-loop <!]]
            [aoc2019.intcode :as intcode]))

(defn phase-settings [low high]
  (l/run* [a b c d e]
    (l/everyg #(fd/in % (fd/interval low high)) [a b c d e])
    (l/distincto [a b c d e])))

(defn run-series [intcodes phases]
  (let [->a  (chan)
        a->b (chan)
        b->c (chan)
        c->d (chan)
        d->e (chan)
        e-> (chan)
        halt-ch (chan)]
    (let [chans [->a a->b b->c c->d d->e]]
      (dotimes [n 5]
        (put! (nth chans n) (nth phases n)))
      (put! ->a 0))
    (intcode/process intcodes ->a a->b halt-ch)
    (intcode/process intcodes a->b b->c halt-ch)
    (intcode/process intcodes b->c c->d halt-ch)
    (intcode/process intcodes c->d d->e halt-ch)
    (intcode/process intcodes d->e e-> halt-ch)
    (<!! e->)))

(defn solve1 [intcodes]
  (transduce (map (partial run-series intcodes)) max 0 (phase-settings 0 4)))

(defn run-loop [intcodes phases]
  (let [a->b (chan)
        b->c (chan)
        c->d (chan)
        d->e (chan)
        e->a (chan)
        halt-ch (chan)]
    (let [chans [e->a a->b b->c c->d d->e]]
      (dotimes [n 5]
        (put! (nth chans n) (nth phases n)))
      (put! e->a 0))
    (intcode/process intcodes e->a a->b halt-ch)
    (intcode/process intcodes a->b b->c halt-ch)
    (intcode/process intcodes b->c c->d halt-ch)
    (intcode/process intcodes c->d d->e halt-ch)
    (intcode/process intcodes d->e e->a halt-ch)
    (<!! (go-loop [halts 0]
           (if (= halts 4)
             (<! e->a)
             (do (<! halt-ch)
                 (recur (inc halts))))))))

(defn solve2 [intcodes]
  (transduce (map (partial run-loop intcodes)) max 0 (phase-settings 5 9)))
