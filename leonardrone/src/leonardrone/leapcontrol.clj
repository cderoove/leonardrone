(ns leonardrone.leapcontrol
  (:require [clojure-leap.core :as leap]
            [clojure-leap.screen :as l-screen]
            [clojure-leap.gestures :as gestures])
  (:import (java.awt Robot)))

(def active? (atom false)) ;; Should we be reacting to the leap?
(def toggle-threshold (atom 0)) ;; Remove thrashing from sliding-window gesture detection
(def robot (Robot.))

(defn toggle! [hit-it? frame]
  (when (and hit-it?
             (> (.id frame) @toggle-threshold)) ;; we're beyond the window of a previous gesture
    (reset! active? (not @active?))
    (reset! toggle-threshold (+ gestures/*threshold* (.id frame))) ;; thresholds are (* 1.5 gesture-window)
    (println "Drone pen active:" @active?)))

(defn process-frame [controller frame screens toggle]
  (let [fingers (leap/fingers frame)
        toggle-switch? (gestures/finger-flash? (leap/frames controller gestures/*window*))
        _ (toggle! toggle-switch? frame)]
    (when-let [pointable (and (leap/pointables? frame) (first (leap/pointables frame)))]
      (let [position-map (l-screen/intersect-position screens pointable)]
        ;(.mouseMove robot (:x position-map)(:y position-map))
        position-map))))


