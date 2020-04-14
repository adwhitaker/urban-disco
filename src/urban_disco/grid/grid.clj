(ns urban-disco.grid.grid
  (:require [grid.constants :as constants]
            [grid.tile      :as tile])
  (:gen-class))

(defn center-tile [grid]
  "Returns the center tile of a grid"
  (nth grid (-> grid count dec (/ 2) Math/ceil int)))

(defn explore [grid x y]
  "Sets the explored property to true"
  (assoc-in grid [(tile/index x y constants/default-grid-height) :explored] true))

(defn update-group [grid tile group]
  "Sets the group of a tile at a given position"
  (if (not (nil? tile))
    (let [index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
      (assoc-in grid [index :group] group))
    grid))

(defn get-tile [grid x y]
  "Returns the tile at the given coordinates from the grid"
  (nth grid (tile/index x y constants/default-grid-height)))

(defn- remove-wall [grid tile direction]
  "Removes a tile's wall from a given direction"
  (let [tile-index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
    (assoc-in grid [tile-index direction] true)))

(defn remove-neighboring-walls [grid a b]
  "Removes the wall between two neighboring cells"
  (if (and (not (nil? a)) (not (nil? b)))
    (let [x (- (:x a) (:x b))
          y (- (:y a) (:y b))]
      (cond
        (= x  1) (-> grid (remove-wall a :left)  (remove-wall b :right))
        (= x -1) (-> grid (remove-wall a :right) (remove-wall b :left))
        (= y  1) (-> grid (remove-wall a :up)    (remove-wall b :down))
        (= y -1) (-> grid (remove-wall a :down)  (remove-wall b :up))))
    grid))

(defn remove-tile [grid tile]
  "Removes a tile from the grid"
  (if (not (nil? tile))
    (reduce (fn [out cell]
              (if (and (= (:x cell) (:x tile)) (= (:y cell) (:y tile)))
                out
                (conj out cell)))
            []
            grid)
    grid))

(defn rand-tile [grid filter-fn]
  "Returns a random tile from a filtered grid"
  (rand-nth grid))
