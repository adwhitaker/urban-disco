(ns grid.base-grid
  (:require [grid.constants :as constants])
  (:gen-class))

(defn build-tile [x y]
  "Generates a base grid tile"
  {:x        x
   :y        y
   :explored false
   :group    nil
   :up       false
   :right    false
   :down     false
   :left     false})

(defn- build-row [width row-number]
  "Generates a row of tiles"
  (let [row (range width)]
    (map #(build-tile % row-number) row)))

(defn build-grid
  "Generates a base grid, off which to build a maze"
  ([] (build-grid constants/default-grid-height))
  ([height]
   (loop [rows (range height)
          grid []]
     (if (not-empty rows)
       (recur (drop 1 rows) (into grid (build-row height (first rows))))
       grid))))
