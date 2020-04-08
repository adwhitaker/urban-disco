(ns grid.neighbors
  (:require [grid.constants :as constants]
            [grid.grid      :as grid]
            [grid.tile      :as tile])
  (:gen-class))

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

(defn rand-unexplored-neighbor [grid tile]
  "Returns a random unexplored neighbor, if available"
  (let [neighbors (all-unexplored-neighbors grid tile)]
    (when (not-empty neighbors)
      (first (shuffle neighbors)))))

(defn unexplored-neighbors? [grid tile]
  "Determines if tile has an unexplored neighbor"
  (let [neighbors (all-unexplored-neighbors grid tile)]
    (not-empty neighbors)))

(defn by-unexplored-neighbors [grid tiles]
  "Determines what tiles from a vector has unexplored neighbors"
  (reduce (fn [out tile]
            (let [has-neighbors (unexplored-neighbors? grid tile)]
              (if (true? has-neighbors)
                (conj out tile)
                out)))
          []
          tiles))
