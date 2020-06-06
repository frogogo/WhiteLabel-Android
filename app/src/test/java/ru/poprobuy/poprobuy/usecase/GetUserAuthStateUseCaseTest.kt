package ru.poprobuy.poprobuy.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.data.local.UserPreferences
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase.State
import kotlin.test.assertEquals

class GetUserAuthStateUseCaseTest {

  private lateinit var getUserAuthStateUseCase: GetUserAuthStateUseCase
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @Before
  fun startUp() {
    getUserAuthStateUseCase = GetUserAuthStateUseCase(userPreferences)
  }

  /**
   * User launched the application for the first time (or didn't passed onboarding)
   * then [State.CLEAN_START] should be returned
   */
  @Test
  fun `first launch`() {
    assertEquals(State.CLEAN_START, getUserAuthStateUseCase())
  }

  /**
   * User already viewed onboarding but not authorized
   * then [State.ONBOARDING_VIEWED] should be returned
   */
  @Test
  fun `onboarding viewed`() {
    every { userPreferences.onboardingViewed } returns true
    assertEquals(State.ONBOARDING_VIEWED, getUserAuthStateUseCase())
  }

  /**
   * User already viewed onboarding and authorized
   * then [State.LOGGED_IN] should be returned
   */
  @Test
  fun `user authorized`() {
    every { userPreferences.isLoggedIn } returns true
    every { userPreferences.onboardingViewed } returns true
    assertEquals(State.LOGGED_IN, getUserAuthStateUseCase())
  }

  /**
   * User has not viewed onboarding (how?) but authorized
   * then [State.LOGGED_IN] should be returned
   */
  @Test
  fun `user authorized but onboarding was not viewed`() {
    every { userPreferences.isLoggedIn } returns true
    every { userPreferences.onboardingViewed } returns false

    assertEquals(State.LOGGED_IN, getUserAuthStateUseCase())
  }

}
