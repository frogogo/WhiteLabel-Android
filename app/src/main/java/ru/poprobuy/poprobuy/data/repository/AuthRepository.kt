package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class AuthRepository(
  private val userPreferences: UserPreferences
) {

  fun setPolicyAccepted() {
    userPreferences.policyAccepted = true
  }

}
