(ns urban-disco.core
  (:gen-class))

(defn build-tile [x y] 
  {:x        x
   :y        y
   :explored false})

(defn build-row [width row-number]
  (let [row (range width)]
    (into [] (map #(build-tile % row-number) row))))

(defn build-grid
  ([] (build-grid 5))
  ([height] 
   (let [grid (range height)]
     (into [] (map #(build-row height %) grid)))))

(defn get-tile [grid x y]
  (nth (nth grid y) x))

(defn calc-center [height]
  (-> height dec (/ 2) Math/ceil int))

(defn calc-vector-center [grid]
  (-> grid count calc-center))

(defn grid-center [grid]
  (let [x (calc-vector-center grid)
        y (calc-vector-center (first grid))]
    (get-tile grid x y)))

(defn calc-starting-tile
  ([] (calc-starting-tile 5))
  ([height]
   (let [center (calc-center height)]
     {:x center :y center})))

(defn is-tile-explored [tile]
  (get tile :explored))

(defn set-tile-explored [grid x y]
  (assoc-in grid [y x :explored] true))

(defn explore-tile [state x y]
  (assoc-in state [:grid y x :explored] true))

(defn set-current-tile [state x y]
  (assoc state :current-position (get-tile (:grid state) x y)))

(defn move-up [state]
  (let [y (get-in state [:current-position :y])
        x (get-in state [:current-position :x])
        new-y (- y 1)]
    (if (> y 0)
      (-> state
          (explore-tile x new-y)
          (set-current-tile x new-y))
      state)))

(defn move [state direction]
  (case direction
    :up
    (move-up state)
    state))

(def base-grid (set-tile-explored (build-grid) 2 2))
(def starting-position (grid-center base-grid))

(def state {
            :grid             base-grid
            :current-position starting-position})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
