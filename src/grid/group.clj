(ns grid.group
  (:require [grid.constants :as constants]
            [grid.neighbors :as neighbor]
            [grid.grid      :as grid]
            [grid.tile      :as tile])
  (:gen-class))

(defn all-different-group-neighbors [grid tile]
  (let [{:keys [top right bottom left]} (neighbor/neighbor-indexes grid tile)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0) (tile/same-group? tile (nth grid neighbor-index)))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))


(defn find-all-different-groups [grid tiles]
    (reduce (fn [out tile]
        (let [neighbors (all-different-group-neighbors grid tile)]
            (if (not-empty neighbors)
                (into out neighbors)
                out))
        []
        tiles)))

(defn remove-neighboring-wall [grid tile]
    (let [neighbors (all-different-group-neighbors)]
        (if (not-empty neighbors)
            (grid/remove-neighboring-walls grid tile (first neighbors))
            grid)))

(defn merge-groups [grid start goal]
  (let [group-neighbors (find-all-different-groups grid start)
        total-to-remove (-> group-neighbors count (* 80) (/ 100) Math/floor int)]
        (loop [i 0
               final-grid grid]
            (if (< i total-to-remove)
                (recur (inc i) (-> final-grid (remove-neighboring-wall (nth group-neighbors i))))
                (tile/tiles->game-tiles final-grid)))))
