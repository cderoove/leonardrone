(ns leonardrone.core
  (:require [leonardrone.leapcontrol :as leapcontrol])
  (:require [clojure-leap.core :as leap])
  (:require [leanordrone.drone :as drone]))

(def x (atom 0.5))
(def y (atom 0.5))

(defn move
  [dx dy]
  (left dx)
  (up dy))

(defn frame-processing
  [something]
  (let [position-map (leapcontrol/process-frame (:controller %) (:frame %) (:screens %))
        current-x (:x position-map)
        current-y (:y position-map)]
    (move (- x current-x) (- y current-y))
    (swap! x current-x)
    (swap! y current-x)))

(defn -main [& args]
  (let [listener (leap/listener
                   :frame frame-processing
                   :default #(println "Toggling" (:state %) "for listener:" (:listener %)))
        [controller _] (leap/controller listener)]
    (add-watch leapcontrol/active? (fn [_ _ _ v] (if v (drone/pos1) (drone/pos2))))
    (println "Press Enter to quit")
    (read-line)
    (leap/remove-listener! controller listener)))
