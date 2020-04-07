(ns grid.tile-test
  (:require [clojure.test :refer :all]
            [grid.base-grid :only ['build-grid 'build-cell]]
            [grid.tile :refer :all]))

(deftest index-test
  (testing "when x and y are in bounds"
    (is (= 3 (index 3 0 5))))
  (testing "when x is out of bounds"
    (is (= -1 (index -1 0 5)))
    (is (= -1 (index 5 0 5)))
    (is (= -1 (index 10 0 5))))
  (testing "when y is out of bounds"
    (is (= -1 (index 0 -1 5)))
    (is (= -1 (index 0 5 5)))
    (is (= -1 (index 0 10 5)))))

(deftest get-tile-test
  (let [grid (build-grid)]
    (testing "returns expected tile"
      (let [{:keys [x y]} (get-tile grid 3 0)]
        (is (= 3 x))
        (is (= 0 y)))
      (let [{:keys [x y]} (get-tile grid 2 4)]
        (is (= 2 x))
        (is (= 4 y))))))

(deftest center-tile-test
  (let [grid (build-grid)]
    (testing "returns center tile"
      (let [{:keys [x y]} (center-tile grid)]
        (is (= 2 x))
        (is (= 2 x))))))

(deftest explored?-test
  (testing "when explored is true, returns true"
   (let [tile (assoc-in (build-cell 2 2) [:explored] true)]
     (is (true? (explored? tile)))))
  (testing "when explored is false, returns false"
    (let [tile (build-cell 2 3)]
      (is (false? (explored? tile))))))

(deftest explore-test
  (testing "sets tile in grid as explored"
    (let [grid (build-grid)]
      (is (true? (explored? (get-tile (explore grid 3 3) 3 3)))))))
