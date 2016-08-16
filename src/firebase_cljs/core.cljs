(ns firebase-cljs.core
  (:require [cljsjs.firebase]
            [firebase-cljs.app]
            [firebase-cljs.auth]
            [firebase-cljs.database]
            [firebase-cljs.error]
            [firebase-cljs.promise]
            [firebase-cljs.user]
            [firebase-cljs.storage]
            ))

(def fb js/firebase)

;; Helpers
(def ->cljs #(js->clj % :keywordize-keys true))

;; Functions
(defn init
  ([opts] (.. fb (initializeApp (clj->js opts))))
  ([opts aname] (.. fb (initializeApp (clj->js opts) aname))))

(defn get-app
  ([] (.. fb app))
  ([aname] (.. fb (app aname))))

(defn get-auth
  ([] (.. fb auth))
  ([app] (.. fb (auth app))))

(defn get-db
  ([] (.. fb database))
  ([app] (.. fb (database app))))

(defn get-storage
  ([] (.. fb storage))
  ([app] (.. fb (storage app))))

;; Firebase Properties
(defn get-apps []
  (->cljs (aget fb "apps")))

(defn get-version []
  (->cljs (aget fb "SDK_VERSION")))
