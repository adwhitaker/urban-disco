(ns urban-disco.core
  (:gen-class))



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
  (first (shuffle unexplored)))

(defn update-rooms [state current-room new-room dir]
  (assoc-in state [:rooms current-room dir] (:name new-room)))


(defn move [state direction]
  (let [current-room ((:rooms state) (:current-room state))]
    (case (direction current-room)

      :unexplored
      (let [new-room (get-unexplored-room state)]
        (update-rooms [state (:name current-room) (:name new-room) direction])))


      ;; else
      state
    ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
