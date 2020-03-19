(ns urban-disco.core
  (:gen-class))

(defn build-tile [x y] 
  {:x        x
   :y        y
   :explored false})

(defn build-row [width row-number]
  (let [row (range width)]
    (map #(build-tile % row-number) row)))

(defn build-grid
  ([] (build-grid 5))
  ([height] 
   (let [grid (range height)]
     (map #(build-row height %) grid))))

(defn get-tile [grid x y]
  (nth (nth grid y) x))

(defn calc-vector-center [grid]
  (-> grid count dec (/ 2) Math/ceil int))

(defn grid-center [grid]
  (let [x (calc-vector-center grid)
        y (calc-vector-center (first grid))]
    (get-tile grid x y)))

(defn is-tile-explored [tile]
  (get tile :explored))

(defn set-tile-explored [grid tile]
  (let [x (get tile :x)
        y (get tile :y)]
    (assoc-in grid [y x :explored] true)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
