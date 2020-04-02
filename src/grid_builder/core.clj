(ns grid-builder.core
  (:gen-class))

(def grid-height-width 5)

(defn index [x, y, columns]
  (if (or (< x 0) 
          (< y 0) 
          (> x (- columns 1)) 
          (> y (- columns 1)))
    -1
    (+ (* y columns) x)))

(defn build-tile [x y grid-height] 
  {:x        x
   :y        y
   :explored false
   :group    nil
   :up       (if (= y 0) :wall :door)
   :down     (if (= (+ y 1) grid-height) :wall :door)
   :left     (if (= x 0) :wall :door)
   :right    (if (= (+ x 1) grid-height) :wall :door)})

(defn build-row [width row-number]
  (let [row (range width)]
    (into [] (map #(build-tile % row-number width) row))))

(defn build-grid
  ([] (build-grid grid-height-width))
  ([height] 
   (let [grid (range height)]
     (into [] (map #(build-row height %) grid)))))

(def unexplored-tiles (into [] (flatten (build-grid))))

(defn nil-group? [tile]
  (nil? (:group tile)))

(defn unexplored-neighbors [tile grid]
  (let [top    (index (:x tile)       (- (:y tile) 1) grid-height-width)
        right  (index (+ 1 (:x tile)) (:y tile)       grid-height-width)
        bottom (index (:x tile)       (+ 1 (:y tile)) grid-height-width)
        left   (index (- (:x tile) 1) (:y tile)       grid-height-width)]
    (reduce (fn [out neighbor-index]
              (if (and (>= neighbor-index 0) (nil-group? (nth grid neighbor-index)))
                (conj out (nth grid neighbor-index))
                out))
            []
            [top right bottom left])))

(defn unexplored-neighbor [tile grid]
  (let [neighbors (unexplored-neighbors tile grid)]
    (if (not-empty neighbors)
      (first (shuffle neighbors))
      nil)))

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

(defn generate-rooms [grid start-tile goal-tile]
  (loop [start [start-tile]
         goal [goal-tile]
         unexplored (-> grid (remove-tile start-tile) (remove-tile goal-tile))]
    (if (empty? unexplored)
      unexplored
      (let [st 3
            gt 3]
       (recur (conj start st) (conj goal gt) unexplored)))))


(def start-tile {
                 :x 3
                 :y 4})

(def goal-tile { 
                :x (rand-int 5) 
                :y (rand-int 3)})
