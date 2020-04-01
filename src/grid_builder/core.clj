(ns grid-builder.core
  (:gen-class))

(defn index [x, y, columns]
  (if (or (< x 0) 
          (< y 0) 
          (> x (- columns 1)) 
          (> y (- columns 1)))
    -1
    (+ (* x columns) y)))

(defn build-tile [x y grid-height] 
  {:x        x
   :y        y
   :explored false
   :up       (if (= y 0) :wall :door)
   :down     (if (= (+ y 1) grid-height) :wall :door)
   :left     (if (= x 0) :wall :door)
   :right    (if (= (+ x 1) grid-height) :wall :door)})

(defn build-row [width row-number]
  (let [row (range width)]
    (into [] (map #(build-tile % row-number width) row))))

(defn build-grid
  ([] (build-grid 5))
  ([height] 
   (let [grid (range height)]
     (into [] (map #(build-row height %) grid)))))

(def unexplored-tiles (into [] (flatten (build-grid))))

(defn generate-rooms [grid]
  (loop [unexplored grid]
    (if (zero? (count unexplored))
      unexplored
      (recur (pop unexplored)))))


(def start-tile {
                 :x 3
                 :y 4})

(def goal-tile { 
                :x (rand-int 5) 
                :y (rand-int 3)})
