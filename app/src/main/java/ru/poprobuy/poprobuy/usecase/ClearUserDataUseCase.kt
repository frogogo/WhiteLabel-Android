package ru.poprobuy.poprobuy.usecase

import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class ClearUserDataUseCase(
  private val userPreferences: UserPreferences,
) {

  operator fun invoke() {
    userPreferences.clearUserData()
  }

}
