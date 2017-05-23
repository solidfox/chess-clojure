(ns chess.mutator
  "Mutation of the state."
  (:use [clojure.test :only (deftest is run-tests function?)]
        [clojure.repl :only (doc)]
        [clojure.pprint :only [pprint]]
        [test.core :only [is= is-not]])
  (:require [chess.core :as core]
            [chess.state :as s]))

(defn create-game! [game-atom]
  (first (reset! game-atom (list (core/create-classic-game-state)))))

(defn move! [game-atom player-id from-position to-position]
  (first (swap! game-atom conj (core/move (first @game-atom) player-id from-position to-position))))

(defn castle! [game-atom player-id from-position to-position]
  (first (swap! game-atom conj (core/castle (first @game-atom) player-id from-position to-position))))

(defn undo! [game-atom player-id]
  (first (swap! game-atom (fn [states] (drop 1 states)))))

(defn get-game [game-atom]
  (first @game-atom))



(deftest A-simple-game
  (let [game-atom (atom nil)]
    (create-game! game-atom)
    (move! game-atom :large [6 4] [4 4])
    (is= (:type (s/get-piece (get-game game-atom) [4 4])) :pawn)
    (move! game-atom :small [0 1] [2 0])
    (is= (:type (s/get-piece (get-game game-atom) [2 0])) :knight)
    (undo! game-atom :small)
    (is= (:type (s/get-piece (get-game game-atom) [0 1])) :knight)))
