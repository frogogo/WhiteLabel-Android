package ru.poprobuy.poprobuy.data.preferences

import ru.poprobuy.poprobuy.data.model.api.user.User

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
