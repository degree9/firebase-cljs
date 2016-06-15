(ns firebase-cljs.error
  (:require [cljsjs.firebase]))

;(defprotocol FirebaseError

;  (get-code [_] "Error codes are strings using the following format: 'service/string-code'")

;  (get-message [_] "An explanatory message for the error that just occurred.")

;  (get-name [_] "The name of the class of Errors.")

;  (get-stack [_] "A string value containing the execution backtrace when the error originally occured."))


;(extend-type js/firebase.FirebaseError

;  FirebaseError
  (defn get-code [app] (.. app -code))

  (defn get-message [app] (.. app -message))

  (defn get-name [app] (.. app -name))

  (defn get-stack [app] (.. app -stack))
;)
