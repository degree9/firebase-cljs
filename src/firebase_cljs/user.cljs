(ns firebase-cljs.user
  (:refer-clojure :exclude [name remove])
  (:require [cljsjs.firebase]))

(defprotocol FirebaseUser

  (verified? [_] "True if the user's email address has been verified.")

  (anonymous? [_] "True if user is anonymous.")

  (get-providerdata [_] "Additional provider-specific information about the user.")

  (get-refreshtoken [_] "A refresh token for the user account. Use only for advanced scenarios that require explicitly refreshing tokens.")

  (remove [_] "Deletes and signs out the user.")

  (get-token [_] [_ refresh] "Returns a JWT token used to identify the user to a Firebase service.")

  (link [_ cred] "Links the user account with the given credentials.")

  (link-popup [_ provider] "Links the authenticated provider to the user account using a pop-up based OAuth flow.")

  (link-redirect [_ provider] "Links the authenticated provider to the user account using a full-page redirect flow.")

  (reauthenticate [_ cred] "Re-authenticates a user using a fresh credential. ")

  (reload [_] "Refreshes the current user, if signed in.")

  (send-verification [_] "Sends a verification email to a user.")

  (unlink [_ provider] "Unlinks a provider from a user account.")

  (update-email [_ email] "Updates the user's email address.")

  (update-password [_ pass] "Updates the user's password.")

  (update-profile [_ profile] "Updates a user's profile data."))

(defprotocol FirebaseUserInfo

  (name [_] "The user's display name (if available).")

  (email [_] "The user's email address (if available).")

  (photo-url [_] "The URL of the user's profile picture (if available).")

  (providerid [_] "The authentication provider ID for the current user. For example, 'facebook.com', or 'google.com'.")

  (uid [_] "The user's unique ID."))

(extend-type firebase.User

  FirebaseUserInfo
  (name [user] (aget user "displayName"))

  (email [user] (aget user "email"))

  (photo-url [user] (aget user "photoURL"))

  (providerid [user] (aget user "providerId"))

  (uid [user] (aget user "uid"))

  FirebaseUser
  (verified? [user] (aget user "emailVerified"))

  (anonymous? [user] (aget user "isAnonymous"))

  (get-providerdata [user] (aget user "providerData"))

  (get-refreshtoken [user] (aget user "refreshToken"))

  (remove [user] (.. user delete))

  (get-token
    ([user] (.. user getToken))
    ([user refresh] (.. user (getToken refresh))))

  (link [user cred] (.. user (link cred)))

  (link-popup [user provider] (.. user (linkWithPopup provider)))

  (link-redirect [user provider] (.. user (linkWithRedirect provider)))

  (reauthenticate [user cred] (.. user (reauthenticate cred)))

  (reload [user] (.. user reload))

  (send-verification [user] (.. user sendEmailVerification))

  (unlink [user provider] (.. user (unlink provider)))

  (update-email [user email] (.. user (updateEmail email)))

  (update-password [user pass] (.. user (updatePassword pass)))

  (update-profile [user profile] (.. user (updateProfile profile))))
