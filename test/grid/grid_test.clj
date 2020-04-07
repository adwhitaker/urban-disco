(ns grid.tile-test
  (:require [clojure.test   :refer :all]
            [grid.grid      :refer :all]
            [grid.base-grid :only ['build-grid]]
            [grid.tile      :as    tile]))

(deftest center-tile-test
  (let [grid (build-grid)]
    (testing "returns center tile"
      (let [{:keys [x y]} (center-tile grid)]
        (is (= 2 x))
        (is (= 2 x))))))

(deftest get-tile-test
  (let [grid (build-grid)]
    (testing "returns expected tile"
      (let [{:keys [x y]} (get-tile grid 3 0)]
        (is (= 3 x))
        (is (= 0 y)))
      (let [{:keys [x y]} (get-tile grid 2 4)]
        (is (= 2 x))
        (is (= 4 y))))))

(deftest remove-wall-test
  (testing "sets tile direction to true"
    (let [grid (build-grid)]
      (is (true? (:up    (get-tile (remove-wall grid (build-cell 3 3) :up   ) 3 3))))
      (is (true? (:right (get-tile (remove-wall grid (build-cell 3 3) :right) 3 3))))
      (is (true? (:down  (get-tile (remove-wall grid (build-cell 3 3) :down ) 3 3))))
      (is (true? (:left  (get-tile (remove-wall grid (build-cell 3 3) :left ) 3 3)))))))


(deftest explore-test
  (testing "sets tile in grid as explored"
    (let [grid (build-grid)]
      (is (true? (tile/explored? (tile/get-tile (explore grid 3 3) 3 3)))))))

