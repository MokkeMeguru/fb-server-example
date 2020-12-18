(defproject server-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]

                 ;; firebase
                 [com.google.firebase/firebase-admin "7.0.1"]

                 ;; handler
                 [ring/ring-jetty-adapter "1.8.2"]
                 [metosin/reitit "0.5.10"]
                 [metosin/reitit-swagger "0.5.10"]
                 [metosin/reitit-swagger-ui "0.5.10"]

                 ;; handler utils
                 [ring-cors "0.1.13"]
                 [ring-logger "1.0.1"]
                 ;; [com.fasterxml.jackson.core/jackson-core "2.11.3"] ;; required!!!
                 [com.fasterxml.jackson.core/jackson-core "2.12.0"]

                 ;; for security
                 [buddy/buddy-hashers "1.7.0"]

                 ;; for json
                 [clj-time "0.15.2"]
                 [cheshire "5.10.0"]
                 ;; to deal with  environment variables
                 [environ "1.2.0"]

                 ;; for integrant-repl
                 [integrant "0.8.0"]
                 [integrant/repl "0.3.2"]

                 ;; for logging
                 [com.taoensso/timbre "5.1.0"]
                 [com.fzakaria/slf4j-timbre "0.3.20"]

                 ;; for database
                 [honeysql "1.0.444"]
                 [seancorfield/next.jdbc "1.1.613"]
                 [hikari-cp "2.13.0"]
                 [org.postgresql/postgresql "42.2.18"]
                 ;; ;; for migration
                 [ragtime "0.8.0"]

                 ;; for others
                 [camel-snake-kebab "0.4.2"]]

  :main ^:skip-aot server-example.core
  :target-path "target/%s"
  :plugins [[lein-environ "1.1.0"]
            [cider/cider-nrepl "0.25.4"]
            [refactor-nrepl "2.5.0"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
