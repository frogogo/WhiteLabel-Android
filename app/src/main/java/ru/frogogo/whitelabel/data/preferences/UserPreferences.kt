package ru.frogogo.whitelabel.data.preferences

import ru.frogogo.whitelabel.data.model.api.user.User

interface UserPreferences {

  var accessToken: String?
  var refreshToken: String?
  var isLoggedIn: Boolean

  // States
  var onboardingCompleted: Boolean
  var policyAccepted: Boolean

  // Data
  var user: User?

  fun clearData()

  fun clearUserData()
}
