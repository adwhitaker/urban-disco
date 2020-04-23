(ns urban-disco.grid.tile
  (:require [urban-disco.grid.constants :as constants])
  (:gen-class))

(defn index
  "Returns the index of the tile in a one-dimensional vector"
  [x y columns]
  (if (or (neg? x)
          (neg? y)
          (> x (dec columns))
          (> y (dec columns)))
    -1
    (+ (* y columns) x)))

(defn explored? [tile]
  (get tile :explored))

(defn nil-group? [tile]
  (nil? (:group tile)))

(defn same-group? [a b]
  (= (:group a) (:group b)))

(defn same-tile? [a b]
  (and (= (:x a) (:x b)) (= (:y a) (:y b))))

(defn tiles->game-tiles [grid]
  (vec (map (fn [tile] {:x        (:x tile)
                        :y        (:y tile)
                        :explored false
                        :up       (:up tile)
                        :right    (:right tile)
                        :down     (:down tile)
                        :left     (:left tile)})
            grid)))

