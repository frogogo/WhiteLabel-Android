package ru.poprobuy.poprobuy.usecase

import ru.poprobuy.poprobuy.data.preferences.UserPreferences

/**
 * Returns user authorization state
 */
class GetUserAuthStateUseCase(
  private val userPreferences: UserPreferences,
) {

  operator fun invoke(): State = when {
    userPreferences.isLoggedIn -> State.LOGGED_IN
    userPreferences.policyAccepted -> State.POLICY_ACCEPTED
    userPreferences.onboardingCompleted -> State.ONBOARDING_COMPLETED
    else -> State.CLEAN_START
  }

  enum class State {
    // User launched app first time or didn't passed onboarding
    CLEAN_START,

    // User has passed an onboarding
    ONBOARDING_COMPLETED,

    // User has accepted the policy
    POLICY_ACCEPTED,

    // User has passed and auth process
    LOGGED_IN
  }
}
