(defproject chess "0.1.0-SNAPSHOT"
  :description "Learning Clojure by constructing the game Chess"
  :license {}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [http-kit "2.1.18"]
                 [compojure "1.5.1"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/spec.alpha "0.1.143"]
                 [com.cognitect/transit-clj "0.8.300"]
                 [ysera "1.0.3"]]
  :main ^:skip-aot chess.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
