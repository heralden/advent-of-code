(ns aoc2019.09
  (:require [clojure.core.async :refer [chan >!! <!!]]
            [aoc2019.intcode :as intcode]))

(defn run [intcodes input]
  (let [in-ch (chan)
        out-ch (chan)
        halt-ch (chan)]
    (intcode/process intcodes in-ch out-ch halt-ch)
    (>!! in-ch input)
    (<!! out-ch)))

(defn solve1 [intcodes]
  (run intcodes 1))

(defn solve2 [intcodes]
  (run intcodes 2))
