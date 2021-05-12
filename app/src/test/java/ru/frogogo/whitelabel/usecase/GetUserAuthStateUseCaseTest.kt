package ru.frogogo.whitelabel.usecase

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.data.preferences.UserPreferences
import ru.frogogo.whitelabel.usecase.GetUserAuthStateUseCase.State

class GetUserAuthStateUseCaseTest {

  private lateinit var useCase: GetUserAuthStateUseCase
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = GetUserAuthStateUseCase(userPreferences)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  /**
   * User launched the application for the first time (or didn't passed onboarding)
   * then [State.CLEAN_START] should be returned
   */
  @Test
  fun `first launch`() {
    useCase() shouldBeEqualTo State.CLEAN_START
  }

  /**
   * User already completed onboarding but not accepted the policy
   * then [State.ONBOARDING_COMPLETED] should be returned
   */
  @Test
  fun `onboarding completed`() {
    every { userPreferences.onboardingCompleted } returns true

    useCase() shouldBeEqualTo State.ONBOARDING_COMPLETED
  }

  /**
   * User already accepted the policy but not authorized
   * then [State.POLICY_ACCEPTED] should be returned
   */
  @Test
  fun `policy accepted`() {
    every { userPreferences.policyAccepted } returns true

    useCase() shouldBeEqualTo State.POLICY_ACCEPTED
  }

  /**
   * User has not completed onboarding (how?) but accepted the policy
   * then [State.POLICY_ACCEPTED] should be returned
   */
  @Test
  fun `policy accepted but onboarding was not completed`() {
    every { userPreferences.onboardingCompleted } returns false
    every { userPreferences.policyAccepted } returns true

    useCase() shouldBeEqualTo State.POLICY_ACCEPTED
  }

  /**
   * User already completed onboarding and authorized
   * then [State.LOGGED_IN] should be returned
   */
  @Test
  fun `user authorized`() {
    every { userPreferences.isLoggedIn } returns true
    every { userPreferences.onboardingCompleted } returns true

    useCase() shouldBeEqualTo State.LOGGED_IN
  }

  /**
   * User has not accepted the policy (how?) but authorized
   * then [State.LOGGED_IN] should be returned
   */
  @Test
  fun `user authorized but onboarding was not completed`() {
    every { userPreferences.isLoggedIn } returns true
    every { userPreferences.policyAccepted } returns false

    useCase() shouldBeEqualTo State.LOGGED_IN
  }
}
