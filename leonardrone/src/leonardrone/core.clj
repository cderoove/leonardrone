(ns leonardrone.core
  (:require [leonardrone.leapcontrol :as leapcontrol])
  (:require [clojure-leap.core :as leap]))

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