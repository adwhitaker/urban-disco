(ns urban-disco.grid.grid
  (:require [urban-disco.grid.constants :as constants]
            [urban-disco.grid.tile      :as tile])
  (:gen-class))

(defn center-tile
  "Returns the center tile of a grid"
  [grid]
  (nth grid (-> grid count dec (/ 2) Math/ceil int)))

(defn explore
  "Sets the explored property to true"
  [grid x y]
  (assoc-in grid [(tile/index x y constants/default-grid-height) :explored] true))

(defn update-group
  "Sets the group of a tile at a given position"
  [grid tile group]
  (if-not (nil? tile)
    (let [index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
      (assoc-in grid [index :group] group))
    grid))

(defn get-tile
  "Returns the tile at the given coordinates from the grid"
  [grid x y]
  (nth grid (tile/index x y constants/default-grid-height)))

(defn remove-wall
  "Removes a tile's wall from a given direction"
  [grid tile direction]
  (let [tile-index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
    (assoc-in grid [tile-index direction] true)))

(defn remove-neighboring-walls
  "Removes the wall between two neighboring cells"
  [grid a b]
  (if (and (not (nil? a)) (not (nil? b)))
    (let [x (- (:x a) (:x b))
          y (- (:y a) (:y b))]
      (cond
        (= x  1) (-> grid (remove-wall a :left)  (remove-wall b :right))
        (= x -1) (-> grid (remove-wall a :right) (remove-wall b :left))
        (= y  1) (-> grid (remove-wall a :up)    (remove-wall b :down))
        (= y -1) (-> grid (remove-wall a :down)  (remove-wall b :up))))
    grid))

(defn remove-tile
  "Removes a tile from the grid"
  [grid tile]
  (if-not (nil? tile)
    (reduce (fn [out cell]
              (if (and (= (:x cell) (:x tile)) (= (:y cell) (:y tile)))
                out
                (conj out cell)))
            []
            grid)
    grid))

