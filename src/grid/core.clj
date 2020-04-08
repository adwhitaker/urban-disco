(ns grid.core
  (:require [grid.constants :as constants]
            [grid.grid      :as grid]
            [grid.tile      :as tile]
            [grid.group     :as group]
            [grid.neighbors :as neighbors]
            [grid.base-grid :as base])
  (:gen-class))


(def base-grid (base/build-grid))
(def starting (grid/get-tile base-grid 4 2))
(def goaling (grid/get-tile base-grid 0 3))

(defn by-unexplored-neighbors [grid tiles]
  "Determines what tiles from a vector has unexplored neighbors"
  (reduce (fn [out tile]
            (if (pos? (count (neighbors/all-unexplored-neighbors grid tile)))
              (conj out tile)
              out))
          []
          tiles))

(defn rand-unexplored [grid tiles]
  (let [unexplored (by-unexplored-neighbors grid tiles)]
    (when (pos? (count unexplored))
      (rand-nth unexplored))))

(defn same-tile? [a b]
  (and (= (:x a) (:x b)) (= (:y a) (:y b))))

(defn neighbor-indexes [tile]
  "Calculates the position of all tile neighbors in a one-dimensional vector"
 {:top    (tile/index (:x tile)       (- (:y tile) 1) constants/default-grid-height)
  :right  (tile/index (+ 1 (:x tile)) (:y tile)       constants/default-grid-height)
  :bottom (tile/index (:x tile)       (+ 1 (:y tile)) constants/default-grid-height)
  :left   (tile/index (- (:x tile) 1) (:y tile)       constants/default-grid-height)})


(defn all-unexplored-neighbors [grid tile]
  "Returns a vector of all unexplored neighbors"
  (let [{:keys [top right bottom left]} (neighbor-indexes tile)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0) (tile/nil-group? (nth grid neighbor-index)))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))

(defn rand-unexplored-neighbor 
  ([grid tile]
   (rand-unexplored-neighbor grid tile nil))
  ([grid tile second-tile]
   (when (not (nil? tile))
     (if (not (nil? second-tile))
       (let [first-tile (rand-nth (all-unexplored-neighbors grid tile))]
        (when (not (same-tile? first-tile second-tile))
          first-tile))   
      (rand-nth (all-unexplored-neighbors grid tile))))))

(defn gen2 [base-grid start-tile goal-tile]
  (loop [start      [start-tile]
         goal       [goal-tile]
         unexplored (-> base-grid (grid/remove-tile start-tile) (grid/remove-tile goal-tile))
         final-grid base-grid
         x nil]
    (if (not-empty unexplored)
      (let [rand-start           (rand-unexplored final-grid start)
            rand-goal            (rand-unexplored final-grid goal)
            rand-start-neighbor  (rand-unexplored-neighbor final-grid rand-start)
            rand-goal-neighbor   (rand-unexplored-neighbor final-grid rand-goal rand-start-neighbor)]
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
