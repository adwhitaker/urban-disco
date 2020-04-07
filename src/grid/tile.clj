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

(defn explored? [tile]
  (get tile :explored))

(defn nil-group? [tile]
  (nil? (:group tile)))

(defn same-group? [a b]
  (= (:group a) (:group b)))
