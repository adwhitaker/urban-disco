(ns grid.tile
  (:require [grid.constants :as constants])
  (:gen-class))

(defn index [x y columns]
  (if (or (< x 0) 
          (< y 0) 
          (> x (- columns 1)) 
          (> y (- columns 1)))
    -1
    (+ (* y columns) x)))

(defn get-tile [grid x y]
  (nth grid (index x y constants/default-grid-height)))

(defn center-tile [grid]
  (nth grid (-> grid count dec (/ 2) Math/ceil int)))

(defn explored? [tile]
  (get tile :explored))

(defn explore [grid x y]
  (assoc-in grid [(index x y constants/default-grid-height) :explored] true))

