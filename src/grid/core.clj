(ns grid.core
  (:require [grid.constants :as constants]
            [grid.tile :as :tile])
  (:gen-class))

(defn update-direction [grid tile direction]
  (let [t-index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
    (assoc-in grid [t-index direction] true)))

(defn remove-wall [grid a b]
  "Removes the wall between two grid cells"
  (let [x (- (:x a) (:x b))
        y (- (:y a) (:y b))]
    (cond
      (= x 1)  (-> grid (update-direction a :left)  (update-direction b :right))
      (= x -1) (-> grid (update-direction a :right) (update-direction b :left))
      (= y 1)  (-> grid (update-direction a :up)    (update-direction b :down))
      (= y -1) (-> grid (update-direction a :down)  (update-direction b :up)))))

(defn nil-group? [tile]
  (nil? (:group tile)))

(defn same-group? [a b]
  (= (:group a) (:group b)))

(defn neighbor-indexes [tile grid]
 {:top    (tile/index (:x tile)       (- (:y tile) 1) constants/default-grid-height)
  :right  (tile/index (+ 1 (:x tile)) (:y tile)       constants/default-grid-height)
  :bottom (tile/index (:x tile)       (+ 1 (:y tile)) constants/default-grid-height)
  :left   (tile/index (- (:x tile) 1) (:y tile)       constants/default-grid-height)})

(defn find-different-groups [tile grid]
  (let [{:keys [top right bottom left]} (neighbor-indexes tile grid)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0) (same-group? tile (nth grid neighbor-index)))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))

(defn rand-diff-group-neighbor [tile grid]
  (let [neighbors (find-different-groups tile grid)]
    (when (not-empty neighbors)
      (first (shuffle neighbors)))))

(defn unexplored-neighbors [tile grid]
  (let [{:keys [top right bottom left]} (neighbor-indexes tile grid)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0) (nil-group? (nth grid neighbor-index)))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))

(defn unexplored-neighbor [tile grid]
  (let [neighbors (unexplored-neighbors tile grid)]
    (when (not-empty neighbors)
      (first (shuffle neighbors)))))

(defn remove-tile [unexplored tile]
  (reduce (fn [out item]
            (if (and (= (:x item) (:x tile)) (= (:y item) (:y tile)))
              out
              (conj out item)))
          []
          unexplored))

(defn tiles-with-unexplored-neighbors [cells grid]
  (reduce (fn [out cell]
            (let [neighbors (unexplored-neighbors cell grid)]
              (if (not-empty neighbors)
                (conj out cell)
                out)))
          []
          cells))

(defn rand-w-un-n [cells grid]
  (rand-nth (tiles-with-unexplored-neighbors cells grid)))

(defn rand-unexplored-neighbor [rand-cell grid]
  (unexplored-neighbor rand-cell grid))

(defn update-group [grid tile group]
  (let [t-index (tile/index (:x tile) (:y tile) constants/default-grid-height)]
    (assoc-in grid [t-index :group] group)))

(defn join-groups [start goal grid]
  {:start start
   :goal  goal})

(defn generate-rooms [grid start-tile goal-tile]
  (loop [start      [start-tile]
         goal       [goal-tile]
         unexplored (-> grid (remove-tile start-tile) (remove-tile goal-tile))
         gridz      grid]
    (if (empty? unexplored)
      (join-groups start goal gridz)
      (let [rand-st (rand-w-un-n start gridz)
            rand-gt (rand-w-un-n goal  gridz)
            st (rand-unexplored-neighbor start gridz)
            gt (rand-unexplored-neighbor goal  gridz)]
       (recur 
        (conj start st) 
        (conj goal gt) 
        (-> unexplored (remove-tile st) (remove-tile gt)) 
        (-> gridz (update-group st :start) (update-group gt :goal) (remove-wall rand-st st) (remove-wall rand-gt gt)))))))


(def start-tile {
                 :x 3
                 :y 4})

(def goal-tile { 
                :x (rand-int 5) 
                :y (rand-int 3)})
