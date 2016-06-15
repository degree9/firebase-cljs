(ns firebase-cljs.storage
  (:refer-clojure :exclude [name time])
  (:require [cljsjs.firebase]))

(defprotocol FirebaseStorage

  (get-app [_] "The app associated with this service.")

  (get-operation-time [_] "The maximum time to retry operations other than uploads or downloads in milliseconds.")

  (get-upload-time [_] "The maximum time to retry uploads in milliseconds.")

  (get-ref [_ path] "Returns a reference for the given path in the default bucket.")

  (get-ref-url [_ path] "Returns a reference for the given absolute URL.")

  (set-operation-time [_ time] "Set maximum operation retry time in milliseconds.")

  (set-upload-time [_ time] "Set maximum upload retry time in milliseconds."))


(extend-type firebase.storage.Storage

  FirebaseStorage
  (get-app [stor] (.. stor -app))

  (get-operation-time [stor] (.. stor -maxOperationRetryTime))

  (get-upload-time [stor] (.. stor -maxUploadRetryTime))

  (get-ref [stor path] (.. stor (ref path)))

  (get-ref-url [stor path] (.. stor (refFromURL path)))

  (set-operation-time [stor time] (.. stor (setMaxOperationRetryTime time)))

  (set-upload-time [stor time] (.. stor (setMaxUploadRetryTime time))))

