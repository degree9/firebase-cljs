(ns firebase-cljs.database.reference
  (:refer-clojure :exclude [ref key val sort-by remove set take take-last])
  (:require [cljsjs.firebase]
            [firebase-cljs.database.query :as query]))

(defprotocol FirebaseDatabaseReference

  (get-key [_] "The key of a given reference.")

  (get-parent [_] "The parent reference of a given reference.")

  (get-root [_] "The root of a given reference.")

  (get-child [_ child] "Returns the child as a Reference.")

  (on-disconnect [_] "Returns onDisconnect object.")

  (push!
    [_]
    [_ val]
    [_ val callback]
    "Generates a new child location using a unique key and returns a Firebase reference to it.")

  (remove!
    [_]
    [_ callback]
    "Removes the data at this Firebase location.")

  (set!
    [_ val]
    [_ val callback]
    "Writes data to this Firebase location.")

  (set-priority!
    [_ priority]
    [_ priority callback]
    "Sets a priority for the data at this Firebase location.")

  (set-with-priority!
    [_ val priority]
    [_ val priority callback]
    "Writes data to this Firebase location. Like set() but also specifies the priority for that data.")

  (transaction
    [_ update]
    [_ update callback]
    [_ update callback locally]
    "Atomically modifies the data at this location.")

  (update!
    [_ obj]
    [_ obj callback]
    "Writes the enumerated children to this Firebase location."))

(extend-type firebase.database.Reference

  query/FirebaseDatabaseQuery
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
     (query/sort-by query sort nil))
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

  (to-str [query] (.. query toString))

  FirebaseDatabaseReference
  (get-key [ref] (.. ref -key))

  (get-parent [ref] (.. ref -parent))

  (get-root [ref] ( .. ref -root))

  (get-child [ref path] (.. ref (child path)))

  (on-disconnect [ref] (.. ref onDisconnect))

  (push!
    ([ref] (.. ref push))
    ([ref val] (.. ref (push val)))
    ([ref val callback] (.. ref (push val callback))))

  (remove!
    ([ref] (.. ref remove))
    ([ref callback] (.. ref (remove callback))))

  (set!
    ([ref val] (.. ref (set val)))
    ([ref val callback] (.. ref (set val callback))))

  (set-priority!
    ([ref priority] (.. ref (setPriority priority)))
    ([ref priority callback] (.. ref (setPriority priority callback))))

  (set-with-priority!
    ([ref val priority] (.. ref (setWithPriority val priority)))
    ([ref val priority callback] (.. ref (setWithPriority val priority callback))))

  (transaction
    ([ref update] (.. ref (transaction update)))
    ([ref update callback] (.. ref (transaction update callback)))
    ([ref update callback locally] (.. ref (transaction update callback locally))))

  (update!
    ([ref obj] (.. ref (update obj)))
    ([ref obj callback] (.. ref (update obj callback)))))
