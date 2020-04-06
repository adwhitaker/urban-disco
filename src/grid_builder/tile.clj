(ns grid-builder.tile
  (:require [grid-builder.constants :as constants])
  (:gen-class))

(defn index [x y columns]
  (if (or (< x 0) 
          (< y 0) 
          (> x (- columns 1)) 
          (> y (- columns 1)))
    -1
    (+ (* y columns) x)))

(defn get [grid x y]
  (nth grid (index x y constants/default-grid-height))

(defn center [grid]
  (nth grid (-> grid count dec (/ 2) Math/ceil int))

(defn explored? [tile]
  (get tile :explored))

(defn explore [grid x y]
  (assoc-in grid [(index x y constants/default-grid-height) :explored] true))
