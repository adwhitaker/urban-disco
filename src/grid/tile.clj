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

(defn tiles->game-tiles [grid]
  "converts a grid building tile to a game tile"
  (into [] (map (fn [tile] {:x        (:x tile)
                            :y        (:y tile)
                            :explored false
                            :up       (:up tile)
                            :right    (:right tile)
                            :down     (:down tile)
                            :left     (:left tile)}))))

