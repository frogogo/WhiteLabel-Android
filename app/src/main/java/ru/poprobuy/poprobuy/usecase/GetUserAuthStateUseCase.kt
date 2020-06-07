package ru.poprobuy.poprobuy.usecase

import ru.poprobuy.poprobuy.data.preferences.UserPreferences

/**
 * Returns user authorization state
 */
class GetUserAuthStateUseCase(
  private val userPreferences: UserPreferences
) {

  operator fun invoke(): State = when {
    userPreferences.isLoggedIn -> State.LOGGED_IN
    userPreferences.onboardingCompleted -> State.ONBOARDING_COMPLETED
    else -> State.CLEAN_START
  }

  enum class State {
    // User has passed and auth process
    LOGGED_IN,

    // User has passed an onboarding
    ONBOARDING_COMPLETED,

    // User launched app first time or didn't passed onboarding
    CLEAN_START
  }

}
