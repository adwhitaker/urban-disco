(ns urban-disco.grid.neighbors-test
  (:require [clojure.test               :refer :all]
            [urban-disco.grid.grid      :refer [get-tile]]
            [urban-disco.grid.base-grid :refer [build-grid]]
            [urban-disco.grid.tile      :as    tile]
            [urban-disco.grid.neighbors :refer :all]))

(deftest neighbor-indexes-test
  (testing "returns neighbor indexes"
    (let [{:keys [top bottom left right]} (neighbor-indexes {:x 3 :y 3})]
      (is (= 13 top))
      (is (= 23 bottom))
      (is (= 17 left))
      (is (= 19 right)))))

(deftest all-unexplored-neighbors-test
  (let [grid (build-grid)
        tile (get-tile grid 0 0)
        unexplored-neighbors (all-unexplored-neighbors grid tile)]
    (testing "finds all unexplored grid neighbors"
      (is (= 2 (count unexplored-neighbors)))
      (let [a (nth unexplored-neighbors 0)
            b (nth unexplored-neighbors 1)]
        (is (= 1 (:x a)))
        (is (= 0 (:y a)))
        (is (= 0 (:x b)))
        (is (= 1 (:y b)))))))
