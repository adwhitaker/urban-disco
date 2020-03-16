(ns urban-disco.core
  (:gen-class))

(defn build-tile [x, y] 
  {:x x
   :y y})

(defn build-row [width row-number]
  (let [row (range width)]
    (map #(build-tile % row-number) row)))

(defn build-grid
  ([] (build-grid 5))
  ([height] 
   (let [grid (range height)]
     (map #(build-row height %) grid))))
(def state {:rooms

            {:entrance {:name      :entrance
                        :up        :foyer
                        :left      :unexplored
                        :right     :unexplored}}
            {:foyer    {:name      :foyer
                        :up        :grand-stairs
                        :left      :unexplored
                        :right     :unexplored
                        :down      :entrance}}
            {:grand-stairs {:name  :grand-stairs
                            :down  :foyer
                            }}



            :unexplored [{:name :kitchen}
                         {:name :organ-room}
                         {:name :dining-room }]


            :haunt-rolls 0

            :current-room :entrance})

(defn get-unexplored-room [{unexplored :unexplored}]
  (let [shuffled (shuffle unexplored)]
    [(first shuffled)
     (rest shuffled)]))

(defn update-rooms [state current-room new-room dir]
  (assoc-in state [:rooms current-room dir] (:name new-room)))

(defn get-current-room [state]
  ((:rooms state) (:current-room state)))

(defn get-available-moves [state]
  (let [current-room (get-current-room state)]
    { :up (:up current-room)
     :down (:down current-room)
     :left (:left current-room)
     :right (:right current-room)}))

(defn opposite-direction [direction]
  (case direction
    :up :down
    :down :up
    :left :right
    :right :left))

(defn move [state direction]
  (let [current-room ((:rooms state) (:current-room state))]
    (case (direction current-room)

      :unexplored
      (let [[new-room unexplored] (get-unexplored-room state)]
        (-> state
            (assoc-in [:rooms (:name current-room) direction] (:name new-room))
            (assoc-in [:rooms (:name new-room) (opposite-direction direction)] (:name current-room))
            (assoc :current-room (:name new-room))
            (assoc :unexplored unexplored)))


      ;; else
      state)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
