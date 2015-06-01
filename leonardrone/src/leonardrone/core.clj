(ns leonardrone.core
  (:require [leonardrone.leapcontrol :as leapcontrol])
  (:require [clojure-leap.core :as leap])
  (:require [quil.core :as q])
  (:require [leanordrone.drone :as drone]))

(def x (atom 0.5))
(def y (atom 0.5))
(def commands (atom []))

(defn move
  [dx dy]
  ; (swap! commands conj (fn [] (left dx) (up dy)))
  (drone/left dx) (drone/back dy))

(defn frame-processing
  [something]
  (let [position-map (leapcontrol/process-frame (:controller %) (:frame %) (:screens %))
        current-x (/ (:x position-map) (:width-px (:dim position-map)))
        current-y (/ (:y position-map) (:height-px (:dim position-map)))]
    (move (- x current-x) (- y current-y))
    (swap! x current-x)
    (swap! y current-y)))

(defn -main [& args]
  (let [listener (leap/listener
                   :frame frame-processing
                   :default #(println "Toggling" (:state %) "for listener:" (:listener %)))
        [controller _] (leap/controller listener)]
    (add-watch leapcontrol/active? (fn [_ _ _ v] (if v (drone/pos1) (drone/pos2))))
    (println "Press Enter to quit")
    (read-line)
    (leap/remove-listener! controller listener)))



;; Processing setup

(defn setup []
  (q/smooth)                        
  (q/frame-rate 1)                    ;; Set framerate to 1 FPS
  (q/background 200))                

(defn draw []
  (q/stroke 100)        
  (q/stroke-weight 5)       
  (q/fill 10)               
  (q/ellipse @x @y 5 5))         

(q/defsketch example                 
  :title "Leonardrone's Eazel"    
  :setup setup                       
  :draw draw                          
  :size [1280 800]) 
