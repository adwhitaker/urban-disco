(ns grid.grid
  (:require [grid.constants :as constants])
  (:gen-class))

(defn center [grid]
  (nth grid (-> grid count dec (/ 2) Math/ceil int))