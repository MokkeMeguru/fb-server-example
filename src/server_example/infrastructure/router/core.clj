(ns server-example.infrastructure.router.core
  (:require
   [reitit.ring :as ring]
   [reitit.core :as reitit]
   [reitit.coercion.spec]

   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [reitit.ring.coercion :as coercion]

   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.ring.middleware.dev :as dev]
   [reitit.ring.spec :as spec]

   [spec-tools.spell :as spell]
   [muuntaja.core :as m]

   [clojure.java.io :as io]

   [ring.logger :refer [wrap-with-logger]]
   [integrant.core :as ig]
   [taoensso.timbre :as timbre]

   [reitit.dev.pretty :as pretty]

   [server-example.infrastructure.router.samples :refer [sample-router]]
   [server-example.infrastructure.router.firebase-auth :refer [firebase-auth-router]]
   [server-example.infrastructure.router.utils :refer [my-wrap-cors]]))

(defn app [env]
  (ring/ring-handler
   (ring/router
    [["/swagger.json"
      {:get {:no-doc true
             :swagger {:info {:title "server example api"}
                       :securityDefinitions
                       {:Bearer
                        {:type "apiKey"
                         :in "header"
                         :name "Authorization"}
                         ;;{:type "http" :scheme "bearer" :name "firebase id token" :in "header"}
                        }
                       :basePath "/"}
             :handler (swagger/create-swagger-handler)}}]
     ["/api"
      (sample-router env)
      (firebase-auth-router env)]]
    {:exception pretty/exception
     :data {:coercion reitit.coercion.spec/coercion
            :muuntaja m/instance
            :middleware [;; swagger feature
                         swagger/swagger-feature
                         ;; query-params & form-params
                         parameters/parameters-middleware
                           ;; content-negotiation
                         muuntaja/format-negotiate-middleware
                           ;; encoding response body
                         muuntaja/format-response-middleware
                           ;; exception handling
                         exception/exception-middleware
                           ;; decoding request body
                         muuntaja/format-request-middleware
                           ;; coercing response bodys
                         coercion/coerce-response-middleware
                           ;; coercing request parameters
                         coercion/coerce-request-middleware
                           ;; multipart
                         multipart/multipart-middleware]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler {:path "/api"})
    (ring/create-default-handler))
   {:middleware [my-wrap-cors
                 wrap-with-logger]}))

(defmethod ig/init-key ::router [_ {:keys [env]}]
  (timbre/info "router got: env" env)
  (app env))
