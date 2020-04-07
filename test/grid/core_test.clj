(ns grid.core-test
  (:require [clojure.test :refer :all]
            [grid.core    :refer :all]))

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
