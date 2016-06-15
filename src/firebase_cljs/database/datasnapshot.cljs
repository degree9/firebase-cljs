(ns firebase-cljs.database.datasnapshot
  (:refer-clojure :exclude [ref key val sort-by remove set take take-last])
  (:require [cljsjs.firebase]))

;(defprotocol FirebaseDatabaseDataSnapshot

;  (get-key [_] "Get DataSnapshot key.")

;  (get-ref [_] "Get DataSnapshot ref.")

;  (get-child [_ child] "Get child DataSnapshot from DataSnapshot.")

;  (exists [_] "Returns True if val != Null.")

;  (for-each [_ action] "Enumerate the top-level children in the DataSnapshot.")

;  (get-priority [_] "Returns priority value as String, Number or Null.")

;  (child? [_ child] "Returns True if DataSnapshot has child.")

;  (children? [_] "Returns True if DataSnapshot has children.")

;  (count-children [_] "Returns children count.")

;  (get-value [_] "Convert the DataSnapshot to a Javascript value (number, boolean, string, Object, Array or null)."))

;(extend-type js/firebase.database.DataSnapshot

;  FirebaseDatabaseDataSnapshot
  (defn get-key [snap] (aget snap "key"))

  (defn get-ref [snap] (aget snap "ref"))

  (defn get-child [snap path] (.. snap (child path)))

  (defn exists [snap] (.. snap exists))

  (defn for-each [snap action] (.. snap (forEach action)))

  (defn get-priority [snap] (.. snap getPriority))

  (defn child? [snap path] (.. snap (hasChild path)))

  (defn children? [snap] (.. snap hasChildren))

  (defn count-children [snap] (.. snap numChildren))

  (defn get-value [snap] (.. snap val))

;)
