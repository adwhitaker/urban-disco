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

(defn set-tile-explored [grid tile]
  (let [x (get tile :x)
        y (get tile :y)]
    (assoc-in grid [y x :explored] true)))

(def base-grid (set-tile-explored (build-grid) (calc-starting-tile)))
(def starting-position (grid-center base-grid))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
