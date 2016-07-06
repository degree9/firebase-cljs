(ns firebase-cljs.database.datasnapshot
  (:refer-clojure :exclude [ref key val sort-by remove set take take-last])
  (:require [cljsjs.firebase]))

(defprotocol FirebaseDatabaseDataSnapshot

  (key [_] "Get DataSnapshot key.")

  (ref [_] "Get DataSnapshot ref.")

  (child [_ child] "Get child DataSnapshot from DataSnapshot.")

  (exists [_] "Returns True if val != Null.")

  (for-each [_ action] "Enumerate the top-level children in the DataSnapshot.")

  (get-priority [_] "Returns priority value as String, Number or Null.")

  (child? [_ child] "Returns True if DataSnapshot has child.")

  (children? [_] "Returns True if DataSnapshot has children.")

  (count-children [_] "Returns children count.")

  (val [_] "Convert the DataSnapshot to a Javascript value (number, boolean, string, Object, Array or null)."))

(extend-type object

  FirebaseDatabaseDataSnapshot
  (key [snap] (aget snap "key"))

  (ref [snap] (aget snap "ref"))

  (child [snap path] (.. snap (child path)))

  (exists [snap] (.. snap exists))

  (for-each [snap action] (.. snap (forEach action)))

  (get-priority [snap] (.. snap getPriority))

  (child? [snap path] (.. snap (hasChild path)))

  (children? [snap] (.. snap hasChildren))

  (count-children [snap] (.. snap numChildren))

  (val [snap] (.val snap)))
