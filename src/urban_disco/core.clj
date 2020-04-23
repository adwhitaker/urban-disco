(ns urban-disco.core
  (:require [urban-disco.grid.base-grid :refer [build-grid]]
            [urban-disco.grid.grid      :refer [get-tile]]
            [urban-disco.grid.core      :refer [generate-grid]]
            [urban-disco.move.core      :refer [move]])
  (:gen-class))

(def base-grid         (build-grid))
(def starting-tile     (get-tile base-grid 4 2))
(def goal-tile         (get-tile base-grid 0 3))
(def game-grid         (generate-grid base-grid starting-tile goal-tile))
(def starting-position (get-tile game-grid 2 4))

(def state {:grid             game-grid
            :current-position starting-position})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Future game!"))
