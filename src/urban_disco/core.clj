(ns urban-disco.core
  (:require [urban-disco.grid.base-grid :as base-grid]
            [urban-disco.grid.tile      :as tile]
            [urban-disco.grid.grid      :as grid])
  (:gen-class))

(def center (grid/center-tile (base-grid/build-grid)))
(def base-grid (grid/explore (base-grid/build-grid) (:x center) (:y center)))
(def starting-position center)

(def state {
            :grid             base-grid
            :current-position starting-position})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
