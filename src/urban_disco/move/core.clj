(ns urban-disco.move.core
  (:require [urban-disco.grid.tile :as tile])
  (:gen-class))

(defn explore-tile [state x y]
  (assoc-in state [:grid (tile/index x y 5) :explored] true))

(defn set-current-tile [state tile-index]
  (assoc state :current-position (get-in state [:grid tile-index])))

(defn move-position [state direction x y]
  (let [current-position (get-in state [:current-position])]
    (if (true? (get current-position direction))
      (-> state
          (explore-tile x y)
          (set-current-tile (tile/index x y 5)))
      state)))

(defn move [state direction]
  (let [{:keys [x y]} (get-in state [:current-position])]
    (case direction
      :up
      (move-position state :up    x       (dec y))
      :down
      (move-position state :down  x       (inc y))
      :left
      (move-position state :left  (dec x) y)
      :right
      (move-position state :right (dec x) y)
      state)))
