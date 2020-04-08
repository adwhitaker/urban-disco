(ns grid.core
  (:require [grid.constants :as constants]
            [grid.grid      :as grid]
            [grid.tile      :as tile]
            [grid.group     :as group]
            [grid.neighbors :as neighbor])
  (:gen-class))

(defn generate-board [base-grid start-tile goal-tile]
  (loop [start      [start-tile]
         goal       [goal-tile]
         unexplored (-> base-grid (grid/remove-tile start-tile) (grid/remove-tile goal-tile))
         final-grid      base-grid]
    (if (empty? unexplored)
      (group/merge-groups final-grid start goal)
      (let [rand-st (grid/rand-tile final-grid start 'neighbors/reduce-by-unexplored-neighbors)
            rand-gt (grid/rand-tile final-grid goal  'neighbors/reduce-by-unexplored-neighbors)
            st (neighbor/rand-unexplored-neighbor final-grid start)
            gt (neighbor/rand-unexplored-neighbor final-grid goal)]
       (recur 
        (conj start st) 
        (conj goal gt) 
        (-> unexplored (grid/remove-tile st) (grid/remove-tile gt)) 
        (-> final-grid (grid/update-group st :start) (grid/update-group gt :goal) (grid/remove-neighboring-walls rand-st st) (grid/remove-neighboring-walls rand-gt gt)))))))
