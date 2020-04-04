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
    (into [] (map #(build-cell % row-number) row))))

(defn build-grid
  ([] (build-grid constants/default-grid-height))
  ([height] 
   (let [grid (range height)]
     (into [] (flatten (map #(build-row height %) grid))))))

