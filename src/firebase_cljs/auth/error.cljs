(ns firebase-cljs.auth.error
  (:require [cljsjs.firebase]))

(defprotocol FirebaseAuthError

  (code [_] "Unique error code.")

  (message [_] "Complete error message."))

(extend-type firebase.auth.Error

  FirebaseAuthError
  (code [err] (aget err "code"))

  (message [err] (aget err "message")))
