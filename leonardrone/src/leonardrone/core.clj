(ns leonardrone.core
  (:require [leonardrone.leapcontrol :as leapcontrol])
  (:require [clojure-leap.core :as leap])
  (:require [quil.core :as q])
  (:require [clj-drone.core :refer :all]))

(def *drone* false)

(defn on
  []
  (when *drone*
    (drone :take-off)))

(defn off
  []
  (when *drone*
    (drone :land)))

(defn pos1
  []
  (when *drone*
    (drone :up 1)
    (drone :up 1)
    (drone :up 1)
    (Thread/sleep 3000)))

(defn pos2
  []
  (when *drone*
    (drone :down 1)
    (drone :down 1)
    (drone :down 1)
    (Thread/sleep 3000)))

(def speed 0.2)

(defn up
  [amount]
  (drone-do-for amount :up speed)
  (Thread/sleep amount))


(defn down
  [amount]
  (drone-do-for amount :down speed)
  (Thread/sleep amount))


(defn left
  [amount]
  (drone-do-for amount :tilt-left speed)
  (Thread/sleep amount))

(defn right
  [amount]
  (drone-do-for amount :tilt-right speed)
  (Thread/sleep amount))

(defn front
  [amount]
  (drone-do-for amount :tilt-front speed)
  (Thread/sleep amount))

(defn back
  [amount]
  (drone-do-for amount :tilt-back speed)
  (Thread/sleep amount))


(def x (atom 0.5))
(def y (atom 0.5))
(def lastx (atom 0.5))
(def lasty (atom 0.5))
(def commands (atom []))

(defn move
  [dx dy]
  (when *drone*
    (println "Move: " dx " " dy)
    (if (> dx 0)
      (left dx)
      (right (- dx)))
    (if (> dy 0)
      (up dy)
      (down (- dy)))))

(defn frame-processing
  [something]
  (let [position-map (leapcontrol/process-frame (:controller something) (:frame something) (:screens something))]
    (when position-map
      (when (and (not (Double/isNaN (:x position-map)))
                 (not (Double/isNaN (:y position-map))))
        (let [current-x (/ (:x position-map) 1280) ;(:width-px (:dim position-map)))
              current-y (/ (:y position-map) 800)] ;(:height-px (:dim position-map)))]
          ;(println position-map current-x current-y)
          (move (- @x current-x) (- @y current-y))
          (swap! lastx (fn [_] @x))
          (swap! lasty (fn [_] @y))
          (swap! x (fn [x] (/ (+ current-x x) 2)))
          (swap! y (fn [y] (/ (+ current-y y) 2))))))))


;; Processing setup

(defn setup []
  (q/smooth)
  (q/frame-rate 50)                    ;; Set framerate to 1 FPS
  (q/background 255))

(defn draw []
  (when @leapcontrol/active?
    ;(println @x @y)
    (q/stroke (q/random 255) (q/random 255) (q/random 255))
    (q/stroke-weight 5)
    (q/fill 10)
    ;(q/ellipse (* @x 1280) (* @y 800) 5 5)
    (q/line (* @lastx 1280) (* @lasty 800)
            (* @x 1280) (* @y 800))))

(q/defsketch example
  :title "Leonardrone's Eazel"
  :setup setup
  :draw draw
  :size [1280 800])

(defn leap []
  (let [listener (leap/listener
                   :frame frame-processing
                   :default #(println "Toggling" (:state %) "for listener:" (:listener %)))
        [controller _] (leap/controller listener)]
    (add-watch leapcontrol/active? :key (fn [_ _ _ v]
                                          (println "Active changed to: " v)
                                          (if v (pos1) (pos2))))
    (println "Press Enter to quit")
    (read-line)
    (off)
    (leap/remove-listener! controller listener)))



(defn start
  []
  (when *drone*
    (drone-initialize)
    (drone :emergency)
    (drone :take-off)))

(defn -main [& args]
  (start)
  (Thread/sleep 1000)
  (leap))
