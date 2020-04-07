(ns grid.tile-test
  (:require [clojure.test   :refer :all]
            [grid.base-grid :only ['build-grid 'build-cell]]
            [grid.tile      :refer :all]))

(deftest center-tile-test
  (let [grid (build-grid)]
    (testing "returns center tile"
      (let [{:keys [x y]} (center-tile grid)]
        (is (= 2 x))
        (is (= 2 x))))))

(deftest remove-wall-test
  (testing "sets tile direction to true"
    (let [grid (build-grid)]
      (is (true? (:up    (get-tile (remove-wall grid (build-cell 3 3) :up   ) 3 3))))
      (is (true? (:right (get-tile (remove-wall grid (build-cell 3 3) :right) 3 3))))
      (is (true? (:down  (get-tile (remove-wall grid (build-cell 3 3) :down ) 3 3))))
      (is (true? (:left  (get-tile (remove-wall grid (build-cell 3 3) :left ) 3 3)))))))