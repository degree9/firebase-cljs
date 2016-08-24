(ns firebase-cljs.database.query
  (:refer-clojure :exclude [ref key val sort-by remove set take take-last])
  (:require [cljsjs.firebase]))

(defprotocol FirebaseDatabaseQuery

  (get-ref [_] "Get Query ref.")

  (end-at
    [_ val]
    [_ val key]
    "Creates a Query with the specified ending point. The generated Query includes children which match the specified ending point.")

  (equal-to
    [_ val]
    [_ val key]
    "Creates a Query which includes children which match the specified value.")

  (take
    [_ limit]
    "Generates a new Query object limited to the first limit number of children.")

  (take-last
    [_ limit]
    "Generates a new Query object limited to the last limit number of children.")

  (off
    [_]
    [_ event]
    [_ event callback]
    [_ event callback failure]
    "Detaches a callback previously attached with on.")

  (on
    [_ event]
    [_ event callback]
    [_ event callback failure]
    "Listens for data changes at a particular location.")

  (once
    [_ event]
    [_ event callback]
    [_ event callback failure]
    "Listens for exactly one event of the specified event type, and then stops listening.")

  (sort-by
    [_ sort]
    [_ sort val]
    "Generates a new Query object ordered by the specified type.")

  (start-at
    [_ val]
    [_ val key]
    "Creates a Query with the specified starting point. The generated Query includes children which match the specified starting point.")

  (to-str
    [_]
    "Returns string URL for this location."))

(extend-type firebase.database.Query

  FirebaseDatabaseQuery
  (get-ref [query] (.. query -ref))

  (end-at
    ([query val]
     (.. query (endAt val)))
    ([query val key]
     (.. query (endAt val key))))

  (equal-to
    ([query val]
     (.. query (equalTo val)))
    ([query val key]
     (.. query (equalTo val key))))

  (take [query limit] (.. query (limitToFirst limit)))

  (take-last [query limit] (.. query (limitToLast limit)))

  (off
    ([query]
     (.. query off))
    ([query event]
     (.. query (off event)))
    ([query event callback]
     (.. query (off event callback)))
    ([query event callback failure]
     (.. query (off event callback failure))))

  (on
    ([query event callback]
     (.. query (on event callback)))
    ([query event callback failure]
     (.. query (on event callback failure))))

  (once
    ([query event]
      (.. query (once event)))
    ([query event callback]
     (.. query (once event callback)))
    ([query event callback failure]
     (.. query (once event callback failure))))

  (sort-by
    ([query sort]
     (sort-by query sort nil))
    ([query sort val]
     (case sort
      :child (.. query (orderByChild val))
      :key (.. query orderByKey)
      :priority (.. query orderByPriority)
      :value (.. query orderByValue))))

  (start-at
    ([query val]
     (.. query (startAt val)))
    ([query val key]
     (.. query (startAt val key))))

  (to-str [query] (.. query toString)))
