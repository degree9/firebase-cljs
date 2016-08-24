(ns firebase-cljs.database
  (:refer-clojure :exclude [reset! swap! conj! get-in])
  (:require [clojure.string :as s]
            [cljsjs.firebase]
            [firebase-cljs.promise :as fbprom]
            [firebase-cljs.database.datasnapshot]
            [firebase-cljs.database.query :as fbquery]
            [firebase-cljs.database.reference :as fbref]))

(defprotocol FirebaseDatabase

  (get-app [_] "The App associated with the Database service instance.")

  (offline [_] "Disconnect from the server (all database operations will be completed offline).")

  (online [_] "(Re)connect to the server and synchronize the offline database state with the server state.")

  (get-ref
    [_]
    [_ path]
    "Returns a reference to the root or the specified path.")

  (get-ref-url
    [_]
    [_ url]
    "Returns a reference to the root or the path specified in url. An exception is thrown if the url is not in the same domain as the current database."))

(extend-type firebase.database.Database

  FirebaseDatabase
  (get-app [db] (.. db -app))

  (offline [db] (.. db goOffline))

  (online [db] (.. db goOnline))

  (get-ref
    ([db] (.. db ref))
    ([db path] (.. db (ref path))))

  (get-ref-url
    ([db] (.. db refFromURL))
    ([db url] (.. db (refFromURL url)))))

;; Matchbox Inspired API

(defprotocol MatchboxDatabase

  (get-in [_ korks] "Get child reference as input type.")

  (reset!
    [_ val]
    [_ val callback]
    "Set a reference value. Accepts reference and promises.")

  (swap!
    [_ fn]
    [_ fn callback]
    "Swap a reference value. Accepts reference and promises.")

  (merge!
    [_ obj]
    [_ obj callback]
    "Merge object into reference.")

  (conj!
    [_ val]
    [_ val callback]
    "Conjoin value onto reference.")

  (remove!
    [_]
    [_ callback]
    "Remove reference.")

  (listen
    [_ type callback]
    [_ korks type callback]
    [_ korks type callback failure]
    "Listen to reference by event 'type'.")

  (listen-promise
    [_ type callback]
    [_ korks type callback]
    "Listen to a promise reference by event 'type'.")

  (listen-once
    [_ type callback]
    [_ korks type callback]
    "Listen to reference event 'type' once.")

  (disable-listener!
    [_ event]
    [_ event callback]
    "Disable listener by event (or event and callback)."))

;; Matchbox API Helpers
(defn korks->path [korks]
  (if (vector? korks)
    (s/join "/" (mapv #(-> % name str) korks))
    korks))

(extend-protocol MatchboxDatabase

  firebase.database.Database
  (get-in [db korks] (get-ref db (korks->path korks)))

  (reset!
    ([db val]
     (reset! (get-ref db) val))
    ([db val callback]
     (reset! (get-ref db) val callback)))

  (swap!
    ([db fn]
     (reset! (get-ref db) fn))
    ([db fn callback]
     (reset! (get-ref db) fn callback)))

  (merge!
    ([db obj]
     (merge! (get-ref db) obj))
    ([db obj callback]
     (merge! (get-ref db) obj callback)))

  (conj!
    ([db val]
     (conj! (get-ref db) val))
    ([db val callback]
     (conj! (get-ref db) val callback)))

  (remove!
    ([db]
     (remove! (get-ref db)))
    ([db callback]
     (remove! (get-ref db) callback)))

  (listen
    ([db type callback]
     (listen (get-ref db) type callback))
    ([db korks type callback]
     (listen (get-ref db) korks type callback))
    ([db korks type callback failure]
     (listen (get-ref db) korks type callback failure)))

  (listen-promise
    ([db type callback]
     (-> db get-ref (fbquery/once type) (fbprom/then callback)))
    ([db korks type callback]
     (-> db (get-in korks) (fbquery/once type) (fbprom/then callback))))

  (listen-once
    ([db type callback]
     (-> db get-ref (listen-once type callback)))
    ([db korks type callback]
     (-> db get-ref (get-in korks) (listen-once type callback))))

  (disable-listener!
    ([db event]
     (disable-listener! (get-ref db) event))
    ([db event callback]
     (disable-listener! (get-ref db) event callback)))

  object
  (get-in [ref korks] (fbref/get-child ref (korks->path korks)))

  (reset!
    ([ref val]
     (fbref/set! ref (clj->js val)))
    ([ref val callback]
     (fbref/set! ref (clj->js val) callback)))

  (swap!
    ([ref fn]
     (fbref/transaction ref fn))
    ([ref fn callback]
     (fbref/transaction ref fn callback)))

  (merge!
    ([ref obj]
     (fbref/update! ref (clj->js obj)))
    ([ref obj callback]
     (fbref/update! ref (clj->js obj) callback)))

  (conj!
    ([ref val]
     (fbref/push! ref (clj->js val)))
    ([ref val callback]
     (fbref/push! ref (clj->js val) callback)))

  (remove!
    ([ref]
     (fbref/remove! ref))
    ([ref callback]
     (fbref/remove! ref callback)))

  (listen
    ([ref type callback]
     (fbquery/on ref type callback))
    ([ref korks type callback]
     (fbquery/on (get-in ref korks) type callback))
    ([ref korks type callback failure]
     (fbquery/on (get-in ref korks) type callback failure)))

  (listen-promise
    ([ref type callback]
     (-> ref (fbquery/once type) (fbprom/then callback)))
    ([ref korks type callback]
     (fbprom/then (fbquery/once (get-in ref korks) type) callback)))

  (listen-once
    ([ref type callback]
     (fbquery/once ref type callback))
    ([ref korks type callback]
     (fbquery/once (get-in ref korks) type callback)))

  (disable-listener!
    ([ref event]
     (fbquery/off ref event))
    ([ref event callback]
     (fbquery/off ref event callback))))
