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
                 ;[clj-drone "0.1.8"]
                 ])


