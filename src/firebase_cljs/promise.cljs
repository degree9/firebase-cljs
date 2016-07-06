(ns firebase-cljs.promise
  (:refer-clojure :exclude [catch resolve promise])
  (:require [cljsjs.firebase]))

(defn promise
  ([] (.Promise js/firebase))
  ([val] (.Promise js/firebase val)))

(defprotocol FirebaseThenable

  (catch [_ callback] "Assign a callback when the Thenable rejects.")

  (then [_ callback]
        [_ callback failure]
        "Assign callback functions called when the Thenable value either resolves, or is rejected."))

(defprotocol FirebasePromise

  (all [_ values] "Convert an array of Promises, to a single array of values. Promise.all() resolves only after all the Promises in the array have resolved.")

  (reject [_ failure] "Return (an immediately) rejected Promise.")

  (resolve [_ value] "Return (an immediately) resolved Promise."))

(extend-type firebase.Promise

  FirebaseThenable
  (catch [prom callback] (.catch prom callback))

  (then
    ([prom callback]
     (.then prom  callback))
    ([prom callback failure]
     (.then prom callback failure)))

  FirebasePromise
  (all [prom values] (.all prom values))

  (reject [prom failure] (.reject prom failure))

  (resolve [prom value] (.resolve prom value)))

(extend-type object

  FirebaseThenable
  (catch [prom callback] (.catch prom callback))

  (then
    ([prom callback]
     (.then prom  callback))
    ([prom callback failure]
     (.then prom callback failure)))

  FirebasePromise
  (all [prom values] (.all prom values))

  (reject [prom failure] (.reject prom failure))

  (resolve [prom value] (.resolve prom value)))
