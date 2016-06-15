(ns firebase-cljs.auth.provider
  (:require [cljsjs.firebase]))

(defn email
  "Email and password auth provider implementation."
  []
  (new js/firebase.auth.EmailAuthProvider))

(defn facebook
  "Facebook auth provider."
  []
  (new js/firebase.auth.FacebookAuthProvider))

(defn github
  "Github auth provider."
  []
  (new js/firebase.auth.GithubAuthProvider))

(defn google
  "Google auth provider."
  []
  (new js/firebase.auth.GoogleAuthProvider))

(defn twitter
  "Twitter auth provider."
  []
  (new js/firebase.auth.TwitterAuthProvider))

(defn scope
  "Add scope to auth provider."
  [provider scope]
  (.. provider (addScope scope)))

(defn scope-email
  "Add email scope to auth provider."
  [auth provider]
  (case provider
    :google (scope auth "https://www.googleapis.com/auth/userinfo.email")
    :facebook (scope auth "email")
    :github (scope auth "user:email")))

(defn scope-profile
  "Add profile scope to auth provider."
  [auth provider]
  (case provider
    :google (scope auth "https://www.googleapis.com/auth/userinfo.profile")
    :facebook (scope auth "public_profile")
    :github (scope auth "")))
