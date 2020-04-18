(ns urban-disco.grid.tile-test
  (:require [clojure.test               :refer :all]
            [urban-disco.grid.base-grid :refer [build-grid build-tile]]
            [urban-disco.grid.tile      :refer :all]))

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

(deftest explored?-test
  (testing "when explored is true, returns true"
   (let [tile (assoc-in (build-tile 2 2) [:explored] true)]
     (is (true? (explored? tile)))))
  (testing "when explored is false, returns false"
    (let [tile (build-tile 2 3)]
      (is (false? (explored? tile))))))


(deftest nil-group?-test
  (testing "when group is nil, returns true"
    (is (true? (nil-group? (build-tile 3 3)))))
  (testing "when groupd is not nil, returns false"
    (is (false? (nil-group? (assoc-in (build-tile 3 3) [:group] :start))))))

(deftest same-group?-test
  (testing "when groups are the same, returns true"
    (let [a (assoc-in (build-tile 2 2) [:group] :start)
          b (assoc-in (build-tile 2 3) [:group] :start)]
      (is (true? (same-group? a b)))))
  (testing "when troups are different, returns false"
    (let [a (assoc-in (build-tile 2 2) [:group] :goal)
          b (assoc-in (build-tile 2 3) [:group] :start)]
      (is (false? (same-group? a b))))))

(deftest same-tile?-test
  (testing "when tiles are the same, return true"
    (let [a (build-tile 2 2)
          b (build-tile 2 2)]
      (is (true? (same-tile? a b)))))
  (testing "when tiles are different, return false"
    (let [a (build-tile 2 2)
          b (build-tile 2 3)]
      (is (false? (same-tile? a b))))))

(deftest tiles->game-tiles-test
  (let [grid [(build-tile 2 3)]]
    (testing "when creating game tiles, it removes :group"
      (is (false? (contains? (nth (tiles->game-tiles grid) 0) :group))))))
