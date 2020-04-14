(ns urban-disco.grid.neighbors-test
  (:require [clojure.test   :refer :all]
            [grid.grid      :refer :all]
            [grid.base-grid :as    builder]
            [grid.tile      :as    tile]
            [grid.neighbors :refer :all]))

(deftest neighbor-indexes-test
  (testing "builds indexes"
    (is (= 13 (:top (neighbor-indexes {:x 3 :y 3}))))))
