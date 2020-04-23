(ns urban-disco.grid.group-test
  (:require [clojure.test               :refer :all]
            [urban-disco.grid.grid      :refer [get-tile update-group]]
            [urban-disco.grid.base-grid :refer [build-grid]]
            [urban-disco.grid.tile      :as    tile]
            [urban-disco.grid.neighbors :refer :all]))

(deftest find-different-group-neighbors-test
  (let [grid                 (-> (build-grid)
                                 (update-group {:x 2 :y 1} :start)
                                 (update-group {:x 1 :y 2} :goal)
                                 (update-group {:x 3 :y 2} :start)
                                 (update-group {:x 2 :y 2} :goal))
        diff-group-neighbors (find-different-group-neighbors 
                              grid 
                              (get-tile grid 2 2) 
                              :start)]
    (testing "returns different group neighbor tiles"
        (is (= 2 (count diff-group-neighbors)))
      (let [{:keys [group]} (get-tile grid 1 2)]
        (is (= :goal group)))
      (let [{:keys [group]} (get-tile grid 2 3)]
        (is (nil? group))))))

