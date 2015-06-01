(ns leonardrone.core
  (:require [leonardrone.leapcontrol :as leapcontrol])
  (:require [clojure-leap.core :as leap])
  (:require [quil.core :as q])
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main [& args]
  (let [listener (leap/listener 
                   :frame #(println (leapcontrol/process-frame (:controller %) (:frame %) (:screens %)))
                              :default #(println "Toggling" (:state %) "for listener:" (:listener %)))
        [controller _] (leap/controller listener)]
    (println "Press Enter to quit")
    (read-line)
    (leap/remove-listener! controller listener)))

(defn setup []
  (q/smooth)                        
  (q/frame-rate 1)                    ;; Set framerate to 1 FPS
  (q/background 200))                


(defn draw []
  (q/stroke 100)        
  (q/stroke-weight 5)       
  (q/fill 10)               
  (let [diam            
        x    (q/random (q/width))       ;; Set the x coord randomly within the sketch
        y    (q/random (q/height))]     ;; Set the y coord randomly within the sketch
    (q/ellipse x y diam diam)))         ;; Draw a circle at x y with the correct diameter

(q/defsketch example                  ;; Define a new sketch named example
  :title "Oh so many grey circles"    ;; Set the title of the sketch
  :setup setup                        ;; Specify the setup fn
  :draw draw                          ;; Specify the draw fn
  :size [1280 800]) 