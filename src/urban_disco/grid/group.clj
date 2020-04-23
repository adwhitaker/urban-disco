(ns urban-disco.grid.group
  (:require [urban-disco.grid.constants :as constants]
            [urban-disco.grid.neighbors :as neighbor]
            [urban-disco.grid.grid      :as grid]
            [urban-disco.grid.tile      :as tile])
  (:gen-class))

(defn find-different-group-neighbors [grid tile group]
  (let [{:keys [top right bottom left]} (neighbor/neighbor-indexes tile)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0)
                       (not= group (:group (nth grid neighbor-index))))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))

(defn find-all-different-groups [grid tiles group]
  (vec (set
        (flatten
         (reduce (fn [out tile]
                   (let [neighbors
                         (find-different-group-neighbors grid tile group)]
                     (if (not-empty neighbors)
                       (conj out neighbors)
                       out)))
                 []
                 tiles)))))

(defn remove-neighboring-wall [grid tile]
  (let [neighbors (find-different-group-neighbors grid tile :goal)]
    (if (not-empty neighbors)
      (grid/remove-neighboring-walls grid tile (first neighbors))
      grid)))

(defn merge-groups [grid start goal]
  (let [group-neighbors
        (find-all-different-groups grid start :start)
        total-to-remove
        (-> group-neighbors count (* 80) (/ 100) Math/floor int)]
    (loop [i 0
           final-grid grid]
      (if (< i total-to-remove)
        (recur
         (inc i)
         (remove-neighboring-wall final-grid (nth group-neighbors i)))
        (tile/tiles->game-tiles final-grid)))))
