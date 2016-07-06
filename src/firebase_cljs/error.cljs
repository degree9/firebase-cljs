(ns firebase-cljs.error
  (:require [cljsjs.firebase]))

(defprotocol FirebaseError

  (get-code [_] "Error codes are strings using the following format: 'service/string-code'")

  (get-message [_] "An explanatory message for the error that just occurred.")

  (get-name [_] "The name of the class of Errors.")

  (get-stack [_] "A string value containing the execution backtrace when the error originally occured."))


(extend-type object

  FirebaseError
  (get-code [app] (aget app "code"))

  (get-message [app] (aget app "message"))

  (get-name [app] (aget app "name"))

  (get-stack [app] (aget app "stack")))
