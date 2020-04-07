(ns urban-disco.core
  (require [grid.base-grid :as grid-builder]
           [grid.tile :as tile])
  (:gen-class))

(defn set-current-tile [state x y]
  (assoc state :current-position (get-in state [:grid y x])))

(defn move-up [state]
  (let [y (get-in state [:current-position :y])
        x (get-in state [:current-position :x])
        new-y (- y 1)
        tile (get-in state [:grid y x])]
    (if (= (:up tile) :door)
      (-> state
          (explore-tile x new-y)
          (set-current-tile x new-y))
      state)))

(defn move-down [state]
  (let [y (get-in state [:current-position :y])
        x (get-in state [:current-position :x])
        new-y (+ y 1)
        tile (get-in state [:grid y x])]
    (if (= (:down tile) :door)
      (-> state
          (explore-tile x new-y)
          (set-current-tile x new-y))
      state)))

(defn move-left [state]
  (let [y (get-in state [:current-position :y])
        x (get-in state [:current-position :x])
        new-x (- x 1)
        tile (get-in state [:grid y x])]
    (if (= (:left tile) :door)
      (-> state
          (explore-tile new-x y)
          (set-current-tile new-x y))
      state)))

(defn move-right [state]
  (let [y (get-in state [:current-position :y])
        x (get-in state [:current-position :x])
        new-x (+ x 1)
        tile (get-in state [:grid y x])]
    (if (= (:left tile) :door)
      (-> state
          (explore-tile new-x y)
          (set-current-tile new-x y))
      state)))

(defn move [state direction]
  (case direction
    :up
    (move-up state)
    :down
    (move-down state)
    :left
    (move-left state)
    :right
    (move-right state)
    state))

(def center (tile/center (base-grid/build-grid)))

(def base-grid (tile/explore (base-grid/build-grid) (:x center) (:y center)))
(def starting-position (grid-center base-grid))

(def state {
            :grid             base-grid
            :current-position starting-position})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
