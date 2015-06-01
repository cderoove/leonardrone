(defproject leonardrone "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main leonardrone.core
  :plugins [[lein-localrepo "0.5.3"]]
  :resource-paths ["leap_lib/LeapJava.jar" "resources"]  
  :jvm-opts  [~(str "-Djava.library.path=leap_lib/:" (System/getenv "LD_LIBRARY_PATH"))]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail javax.jms/jms com.sun.jdmk/jmxtools com.sun.jmx/jmxri]]
                 [org.slf4j/slf4j-log4j12 "1.6.4"]
                 [org.clojure/tools.logging "0.2.3"]
                 [clj-logging-config "1.9.10"]
                 [me.raynes/conch "0.5.0"]
                 ])


