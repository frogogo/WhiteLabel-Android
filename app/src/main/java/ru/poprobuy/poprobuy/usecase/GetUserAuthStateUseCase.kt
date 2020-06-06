package ru.poprobuy.poprobuy.usecase

import ru.poprobuy.poprobuy.data.local.UserPreferences

/**
 * Returns user authorization state
 */
class GetUserAuthStateUseCase(
  private val userPreferences: UserPreferences
) {

  operator fun invoke(): State = when {
    userPreferences.isLoggedIn -> State.LOGGED_IN
    userPreferences.onboardingViewed -> State.ONBOARDING_VIEWED
    else -> State.CLEAN_START
  }

  enum class State {
    // User has passed and auth process
    LOGGED_IN,

    // User has passed an onboarding
    ONBOARDING_VIEWED,

    // User launched app first time or didn't passed onboarding
    CLEAN_START
  }

}
