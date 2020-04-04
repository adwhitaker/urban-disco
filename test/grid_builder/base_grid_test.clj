(ns grid-builder.base-grid-test
  (:require [clojure.test :refer :all]
            [grid-builder.base-grid :refer :all]))


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
