(ns urban-disco.grid.tile-test
  (:require [clojure.test   :refer :all]
            [grid.base-grid :only ['build-grid 'build-cell]]
            [grid.tile      :refer :all]))

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
   (let [tile (assoc-in (build-cell 2 2) [:explored] true)]
     (is (true? (explored? tile)))))
  (testing "when explored is false, returns false"
    (let [tile (build-cell 2 3)]
      (is (false? (explored? tile))))))


(deftest nil-group?-test
  (testing "when group is nil, returns true"
    (is (true? (nil-group? (build-cell 3 3)))))
  (testing "when groupd is not nil, returns false"
    (is (false? (nil-group? (assoc-in (build-cell 3 3) [:group] :start))))))
