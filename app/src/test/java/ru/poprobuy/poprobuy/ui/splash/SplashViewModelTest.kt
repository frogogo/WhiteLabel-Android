package ru.poprobuy.poprobuy.ui.splash

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.ViewModelTestJUnit5
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase

@ExperimentalCoroutinesApi
class SplashViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: SplashViewModel

  private val getUserAuthStateUseCase: GetUserAuthStateUseCase = mockk(relaxed = true)
  private val navigation: SplashNavigation = mockk(relaxed = true)

  @BeforeEach
  fun setUp() {
    viewModel = SplashViewModel(
      getUserAuthStateUseCase = getUserAuthStateUseCase,
      navigation = navigation
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  /**
   * User launched the application for the first time and must be prompted to onboarding
   */
  @Test
  fun `user first launch`() = coroutineTestExtension.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.CLEAN_START

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToOnboarding())
  }

  /**
   * User has completed onboarding and must be prompted to policy
   */
  @Test
  fun `user completed onboarding`() = coroutineTestExtension.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.ONBOARDING_COMPLETED

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToPolicy())
  }

  /**
   * User has accepted the policy and must be prompted to auth
   */
  @Test
  fun `user accepted policy`() = coroutineTestExtension.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.POLICY_ACCEPTED

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToAuth())
  }

  /**
   * User launched the application for the first time and must be prompted to app
   */
  @Test
  fun `user logged in`() = coroutineTestExtension.runBlockingTest {
    every { getUserAuthStateUseCase.invoke() } returns GetUserAuthStateUseCase.State.LOGGED_IN

    viewModel.onCreate()
    advanceUntilIdle()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToApp())
  }

}
