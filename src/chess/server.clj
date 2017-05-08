(ns chess.server
  (:require
    [chess.games :refer :all]
    [org.httpkit.server :refer :all]
    [compojure.core :refer :all]
    [clojure.data.json :as json]
    [chess.mapper :refer :all]))

;; Routes

(defn game-response [view-game]
  {:status  200
   :headers {"Content-Type"                 "text/json; charset=utf-8"
             "Access-Control-Allow-Origin"  "*"
             "Access-Control-Allow-Methods" "*"}
   :body    (json/write-str view-game)})

(defroutes
  chess
  (POST "/createGame" []
    (time (game-response (game->view-game (create-game!)))))
  (POST "/move" {body :body}
    (let [params (json/read-json (slurp body))
          from-position (:from-position params)
          to-position (:to-position params)
          player-id (keyword (:player-id params))]
      (time (game-response (game->view-game (move! player-id from-position to-position))))))
  (POST "/castle" {body :body}
    (let [params (json/read-json (slurp body))
          from-position (:from-position params)
          to-position (:to-position params)
          player-id (keyword (:player-id params))]
      (time (game-response (game->view-game (castle! player-id from-position to-position)))))))


;; Starting & Stopping

(defonce server (atom nil))

(defn stop! []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    (@server :timeout 100)
    (reset! server nil)))

(defn start! []
  (reset! server (run-server chess {:port 8001})))

(defn restart! []
  (stop!)
  (start!))
