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

(defn explored? [tile]
  (get tile :explored))

(defn nil-group? [tile]
  (nil? (:group tile)))

(defn same-group? [a b]
  (= (:group a) (:group b)))

(defn explore [grid x y]
  (assoc-in grid [(index x y constants/default-grid-height) :explored] true))
