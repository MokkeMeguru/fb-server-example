(ns server-example.infrastructure.env
  (:require [environ.core :refer [env]]
            [taoensso.timbre :as timbre]
            [integrant.core :as ig]))

(defmethod ig/init-key ::env [_ _]
  (timbre/info "loading environment via environ")
  (let [database-url (env :database-url)
        running (env :env)]
    (timbre/info "running in " running)
    (timbre/info "database-url " database-url)
    {:database-url database-url
     :running running
     :migration-folder (env :migration-folder)
     :migration-log (env :migration-log)
     :new-migration (= "true" (env :new-migration))}))
