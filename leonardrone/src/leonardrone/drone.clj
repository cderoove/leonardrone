(ns leonardrone.drone
  (:require [clj-drone.core :refer :all]))

(defn on
  []
  (drone :take-off))

(defn off
  []
  (drone :land))

(defn pos1
  []
  (drone :up 0.3))

(defn pos2
  []
  (drone :down 0.3))

(defn left
  [amount]
  (drone-do-for amount :tilt-left 0.1))

(defn right
  [amount]
  (drone-do-for amount :tilt-right 0.1))

(defn front
  [amount]
  (drone-do-for amount :tilt-front 0.1))

(defn back
  [amount]
  (drone-do-for amount :tilt-back 0.1))

