package ru.poprobuy.poprobuy.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.CoroutinesTestRule
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase
import ru.poprobuy.poprobuy.util.Event

@ExperimentalCoroutinesApi
class SplashViewModelTest {

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: SplashViewModel
  private val getUserAuthStateUseCase: GetUserAuthStateUseCase = mockk(relaxed = true)
  private val navigation: SplashNavigation = mockk(relaxed = true)

  @Before
  fun setUp() {
    viewModel = SplashViewModel(getUserAuthStateUseCase, navigation)
  }

  /**
   * User launched the application for the first time and must be prompted to onboarding
   */
  @Test
  fun `user first launch`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.CLEAN_START

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToOnboarding())
  }

  /**
   * User has completed onboarding and must be prompted to policy
   */
  @Test
  fun `user completed onboarding`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.ONBOARDING_COMPLETED

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToPolicy())
  }

  /**
   * User has accepted the policy and must be prompted to auth
   */
  @Test
  fun `user accepted policy`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.POLICY_ACCEPTED

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToAuth())
  }

  /**
   * User launched the application for the first time and must be prompted to app
   */
  @Test
  fun `user logged in`() = coroutineTestRule.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.LOGGED_IN

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToApp())
  }

}
