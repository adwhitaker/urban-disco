(ns urban-disco.grid.tile-test
  (:require [clojure.test               :refer :all]
            [urban-disco.grid.grid      :refer :all]
            [urban-disco.grid.base-grid :only  ['build-grid 'build-tile]]
            [urban-disco.grid.tile      :as    tile]))

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
      (is (true? (:up    (get-tile (remove-wall grid (build-tile 3 3) :up   ) 3 3))))
      (is (true? (:right (get-tile (remove-wall grid (build-tile 3 3) :right) 3 3))))
      (is (true? (:down  (get-tile (remove-wall grid (build-tile 3 3) :down ) 3 3))))
      (is (true? (:left  (get-tile (remove-wall grid (build-tile 3 3) :left ) 3 3)))))))


(deftest explore-test
  (testing "sets tile in grid as explored"
    (let [grid (build-grid)]
      (is (true? (tile/explored? (get-tile (explore grid 3 3) 3 3)))))))


(deftest update-group-test
  (testing "updates grid with group"
    (let [grid           (build-grid)
          tile-to-update (get-tile grid 3 3)]
      (is (= 
           :start 
           (:group (get-tile (update-group grid tile-to-update :start) 3 3))))))
  (testing "returns grid if tile is nil"
    (let [grid           (build-grid)
          tile-to-update (get-tile grid 3 3)]
      (is (nil? (:group (get-tile (update-group grid nil :start) 3 3)))))))


(deftest remove-neighboring-walls-test
  (testing "when tile a is null, grid is not updated"
    (let [grid                         (build-grid)
          a                            nil
          b                            (get-tile grid 2 2)
          updated-grid                 (remove-neighboring-walls grid a b)
          {:keys [up down left right]} (get-tile updated-grid 2 2)]
      (is (false? up))
      (is (false? down))
      (is (false? left))
      (is (false? right))))
  (testing "when tile b is null, grid is not updated"
    (let [grid                         (build-grid)
          a                            (get-tile grid 2 2)
          b                             nil
          updated-grid                 (remove-neighboring-walls grid a b)
          {:keys [up down left right]} (get-tile updated-grid 2 2)]
      (is (false? up))
      (is (false? down))
      (is (false? left))
      (is (false? right))))
  (testing "removes wall to the :up"
     (let [grid          (build-grid)
          a              (get-tile grid 2 2)
          b              (get-tile grid 2 1)
          updated-grid   (remove-neighboring-walls grid a b)
          {:keys [up]}   (get-tile updated-grid 2 2)
          {:keys [down]} (get-tile updated-grid 2 1)]
      (is (true? up))
      (is (true? down))))
  (testing "removes wall to the :down"
     (let [grid          (build-grid)
          a              (get-tile grid 2 2)
          b              (get-tile grid 2 1)
          updated-grid   (remove-neighboring-walls grid b a)
          {:keys [up]}   (get-tile updated-grid 2 2)
          {:keys [down]} (get-tile updated-grid 2 1)]
      (is (true? up))
      (is (true? down))))
  (testing "removes wall to the :left"
    (let [grid            (build-grid)
          a               (get-tile grid 2 2)
          b               (get-tile grid 1 2)
          updated-grid    (remove-neighboring-walls grid a b)
          {:keys [left]}  (get-tile updated-grid 2 2)
          {:keys [right]} (get-tile updated-grid 1 2)]
      (is (true? left))
      (is (true? right))))
  (testing "removes wall to the :right"
    (let [grid            (build-grid)
          a               (get-tile grid 2 2)
          b               (get-tile grid 1 2)
          updated-grid    (remove-neighboring-walls grid b a)
          {:keys [left]}  (get-tile updated-grid 2 2)
          {:keys [right]} (get-tile updated-grid 1 2)]
      (is (true? left))
      (is (true? right)))))


(deftest remove-tile-test
  (let [grid           [(build-tile 0 0) (build-tile 0 1)]
        tile-to-remove (build-tile 0 1)]
    (testing "removes tile from grid vector"
      (let [updated-grid (remove-tile grid tile-to-remove)
            remaining-tile (nth grid 0)]
        (is (= 1 (count updated-grid)))
        (is (and (= 0 (:x remaining-tile))
                 (= 0 (:y remaining-tile))))))))
