(ns urban-disco.grid.core
  (:require [grid.constants :as constants]
            [grid.grid      :as grid]
            [grid.tile      :as tile]
            [grid.group     :as group]
            [grid.neighbors :as neighbors]
            [grid.base-grid :as base])
  (:gen-class))


(def base-grid (base/build-grid))
(def starting-tile (grid/get-tile base-grid 4 2))
(def goal-tile (grid/get-tile base-grid 0 3))

(defn generate-grid [base-grid start-tile goal-tile]
  (loop [start      [start-tile]
         goal       [goal-tile]
         unexplored (-> base-grid (grid/remove-tile start-tile) (grid/remove-tile goal-tile))
         final-grid base-grid]
    (if (not-empty unexplored)
      (let [rand-start           (neighbors/rand-unexplored final-grid start)
            rand-goal            (neighbors/rand-unexplored final-grid goal)
            rand-start-neighbor  (neighbors/rand-unexplored-neighbor final-grid rand-start)
            rand-goal-neighbor   (neighbors/rand-unexplored-neighbor final-grid rand-goal rand-start-neighbor)]
        (recur
         (if (not (nil? rand-start-neighbor))
           (conj start rand-start-neighbor)
           start)
         (if (not (nil? rand-goal-neighbor))
           (conj goal rand-goal-neighbor)
           goal)
         (-> unexplored
             (grid/remove-tile rand-start-neighbor)
             (grid/remove-tile rand-goal-neighbor)) 
         (-> final-grid
             (grid/update-group rand-start-neighbor :start) 
             (grid/update-group rand-goal-neighbor  :goal)
             (grid/remove-neighboring-walls rand-start rand-start-neighbor) 
             (grid/remove-neighboring-walls rand-goal  rand-goal-neighbor))
         rand-start-neighbor))
      (group/merge-groups final-grid start goal)
      )))
