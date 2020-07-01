package ru.poprobuy.poprobuy.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.CoroutinesTestRule
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class SplashViewModelTest {

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val getUserAuthStateUseCase: GetUserAuthStateUseCase = mockk(relaxed = true)
  private val navigation: SplashNavigation = mockk(relaxed = true)
  private lateinit var splashViewModel: SplashViewModel

  @Before
  fun setUp() {
    splashViewModel = SplashViewModel(getUserAuthStateUseCase, navigation)
  }

  /**
   * User launched the application for the first time and must be prompted to onboarding
   */
  @Test
  fun `user first launch`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.CLEAN_START

    splashViewModel.onCreate()
    advanceUntilIdle()

    assertEquals(navigation.navigateToOnboarding(), splashViewModel.navigationLive.value)
  }

  /**
   * User has completed onboarding and must be prompted to policy
   */
  @Test
  fun `user completed onboarding`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.ONBOARDING_COMPLETED

    splashViewModel.onCreate()
    advanceUntilIdle()

    assertEquals(navigation.navigateToPolicy(), splashViewModel.navigationLive.value)
  }

  /**
   * User has accepted the policy and must be prompted to auth
   */
  @Test
  fun `user accepted policy`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.POLICY_ACCEPTED

    splashViewModel.onCreate()
    advanceUntilIdle()

    assertEquals(navigation.navigateToAuth(), splashViewModel.navigationLive.value)
  }

  /**
   * User launched the application for the first time and must be prompted to app
   */
  @Test
  fun `user logged in`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.LOGGED_IN

    splashViewModel.onCreate()
    advanceUntilIdle()

    assertEquals(navigation.navigateToApp(), splashViewModel.navigationLive.value)
  }

}
