(ns grid-builder.core-test
  (:require [clojure.test :refer :all]
            [grid-builder.core :refer :all]))

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

(deftest build-cell-test
  (let [{:keys [x y up right down left explored group]} (build-cell 3 4)]
    (testing "when testing coordinates"
      (is (= 3 x))
      (is (= 4 y)))
    (testing "when testing defaults"
      (is (false? up))
      (is (false? right))
      (is (false? down))
      (is (false? left))
      (is (false? explored))
      (is (nil? group)))))

(deftest build-grid-test
  (let [grid (build-grid)]
    (testing "builds expected grid based on default (5) width"
      (let [{:keys [x y]} (nth grid 3)]
        (is (= x 3))
        (is (= y 0)))
      (let [{:keys [x y]} (nth grid 5)]
        (is (= x 0))
        (is (= y 1)))
      (let [{:keys [x y]} (nth grid 11)]
        (is (= x 1))
        (is (= y 2)))
      (let [{:keys [x y]} (nth grid 17)]
        (is (= x 2))
        (is (= y 3)))
      (let [{:keys [x y]} (nth grid 23)]
        (is (= x 3))
        (is (= y 4))))))

(deftest nil-group-test
  (let [cell (build-cell 3 4)]
    (testing "when group is nil"
      (is (true? (nil-group? cell))))
    (testing "when group is not nil"
      (is (false? (nil-group? (assoc-in cell [:group] :start)))))))

(deftest same-group-test
  (let [cell-a (assoc-in (build-cell 3 4) [:group] :start)
        cell-b (assoc-in (build-cell 4 4) [:group] :goal)]
    (testing "when testing same groups"
      (is (true?  (same-group? cell-a cell-a))))
    (testing "when testing different groups"
      (is (false? (same-group? cell-a cell-b))))))
