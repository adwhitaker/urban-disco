(ns urban-disco.grid.neighbors
  (:require [urban-disco.grid.constants :as constants]
            [urban-disco.grid.grid      :as grid]
            [urban-disco.grid.tile      :as tile])
  (:gen-class))

(defn neighbor-indexes
  "Calculates the position of all tile neighbors in a one-dimensional vector"
  [tile]
  {:top    (tile/index (:x tile)       (- (:y tile) 1) constants/default-grid-height)
   :right  (tile/index (+ 1 (:x tile)) (:y tile)       constants/default-grid-height)
   :bottom (tile/index (:x tile)       (+ 1 (:y tile)) constants/default-grid-height)
   :left   (tile/index (- (:x tile) 1) (:y tile)       constants/default-grid-height)})

(defn all-unexplored-neighbors [grid tile]
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
   (when-not (nil? tile)
     (if-not (nil? second-tile)
       (let [first-tile (rand-nth (all-unexplored-neighbors grid tile))]
         (when-not (tile/same-tile? first-tile second-tile)
           first-tile))
       (rand-nth (all-unexplored-neighbors grid tile))))))

(defn by-unexplored-neighbors [grid tiles]
  (reduce (fn [out tile]
            (if (pos? (count (all-unexplored-neighbors grid tile)))
              (conj out tile)
              out))
          []
          tiles))

(defn rand-unexplored [grid tiles]
  (let [unexplored (by-unexplored-neighbors grid tiles)]
    (when (pos? (count unexplored))
      (rand-nth unexplored))))
