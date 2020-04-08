(ns grid.grid
  (:require [grid.constants :as constants]
            [grid.tile      :as tile])
  (:gen-class))

(defn center-tile [grid]
  (nth grid (-> grid count dec (/ 2) Math/ceil int)))

(defn explore [grid x y]
  (assoc-in grid [(tile/index x y constants/default-grid-height) :explored] true))

(defn get-tile [grid x y]
  (nth grid (tile/index x y constants/default-grid-height)))

(defn remove-wall [grid tile direction]
  (let [tile-index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
    (assoc-in grid [tile-index direction] true)))

(defn remove-neighboring-walls [grid a b]
  "Removes the wall between two neighboring cells"
  (let [x (- (:x a) (:x b))
        y (- (:y a) (:y b))]
    (cond
      (= x  1) (-> grid (remove-wall a :left)  (remove-wall b :right))
      (= x -1) (-> grid (remove-wall a :right) (remove-wall b :left))
      (= y  1) (-> grid (remove-wall a :up)    (remove-wall b :down))
      (= y -1) (-> grid (remove-wall a :down)  (remove-wall b :up)))))

(defn remove-tile [grid tile]
  "Used to remove from the list of unexplored tiles"
  (reduce (fn [out cell]
            (if (and (= (:x cell) (:x tile)) (= (:y cell) (:y tile)))
              out
              (conj out cell)))
          []
          grid))

(defn rand-tile [grid tiles filter-fn]
  (rand-nth (filter-fn grid tiles)))

(defn update-group [grid tile group]
  (let [index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
    (assoc-in grid [index :group] group)))
