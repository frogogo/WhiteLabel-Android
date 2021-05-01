package ru.frogogo.whitelabel.usecase

import ru.frogogo.whitelabel.data.preferences.UserPreferences

class ClearUserDataUseCase(
  private val userPreferences: UserPreferences,
) {

  operator fun invoke() {
    userPreferences.clearUserData()
  }
}
