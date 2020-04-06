(ns grid-builder.tile-test
  (:require [clojure.test :refer :all]
            [grid-builder.tile :refer :all]))

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