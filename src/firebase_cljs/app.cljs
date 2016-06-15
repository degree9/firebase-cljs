(ns firebase-cljs.app
  (:refer-clojure :exclude [name])
  (:require [cljsjs.firebase]))

(defprotocol FirebaseApp

  (name [_] "Get app name.")

  (options [_] "Get app options.")

  (get-auth [_] "Get app auth object.")

  (get-db [_] "Get app database object.")

  (get-storage [_] "Get app storage object."))


(extend-type firebase.app.App

  FirebaseApp
  (name [app] (.. app -name))

  (options [app] (.. app -options))

  (get-auth [app] (.. app auth))

  (get-db [app] (.. app database))

  (get-storage [app] (.. app storage)))
