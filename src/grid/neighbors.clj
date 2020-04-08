(ns grid.neighbors
  (:require [grid.constants :as constants]
            [grid.grid      :as grid]
            [grid.tile      :as tile])
  (:gen-class))

(defn neighbor-indexes [grid tile]
 {:top    (tile/index (:x tile)       (- (:y tile) 1) constants/default-grid-height)
  :right  (tile/index (+ 1 (:x tile)) (:y tile)       constants/default-grid-height)
  :bottom (tile/index (:x tile)       (+ 1 (:y tile)) constants/default-grid-height)
  :left   (tile/index (- (:x tile) 1) (:y tile)       constants/default-grid-height)})

(defn all-unexplored-neighbors [grid tile]
  (let [{:keys [top right bottom left]} (neighbor-indexes grid tile)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0) (tile/nil-group? (nth grid neighbor-index)))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))

(defn rand-unexplored-neighbor [grid tile]
  (let [neighbors (all-unexplored-neighbors grid tile)]
    (when (not-empty neighbors)
      (first (shuffle neighbors)))))

(defn unexplored-neighbors? [grid tile]
  (let [neighbors (all-unexplored-neighbors grid tile)]
    (not-empty neighbors)))

(defn reduce-by-unexplored-neighbors [grid tiles]
  (reduce (fn [out tile]
            (let [has-neighbors (unexplored-neighbors? grid tile)]
              (if (true? has-neighbors)
                (conj out tile)
                out)))
          []
          tiles))
