(ns grid-builder.base-grid
  (:require [grid-builder.constants :as constants])
  (:gen-class))

(defn build-cell [x y] 
  {:x        x
   :y        y
   :explored false
   :group    nil
   :up       false
   :right    false
   :down     false
   :left     false})

(defn build-row [width row-number]
  (let [row (range width)]
    (map #(build-cell % row-number) row)))

(defn build-grid
  ([] (build-grid constants/default-grid-height))
  ([height]
   (loop [rows (range height)
          grid []]
     (if (empty? rows)
       grid
       (recur (drop 1 rows) (into grid (build-row height (first rows))))))))
