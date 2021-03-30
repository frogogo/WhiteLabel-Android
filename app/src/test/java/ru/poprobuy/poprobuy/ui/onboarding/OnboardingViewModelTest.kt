package ru.poprobuy.poprobuy.ui.onboarding

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.core.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.onboarding.OnboardingPage
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository
import ru.poprobuy.test.*
import ru.poprobuy.test.base.ViewModelTestJUnit5

@ExperimentalCoroutinesApi
class OnboardingViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: OnboardingViewModel

  private val onboardingRepository: OnboardingRepository = mockk(relaxed = true)
  private val navigation: OnboardingNavigationImpl = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    every { onboardingRepository.getPages() } returns getOnboardingPages()

    viewModel = OnboardingViewModel(
      onboardingRepository = onboardingRepository,
      navigation = navigation
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `posts proper list to LiveData`() {
    val dataObserver = mockkObserver<List<RecyclerViewItem>>()
    viewModel.pagesLive.observeForever(dataObserver)

    verify {
      dataObserver.onChanged(getOnboardingPages())
    }
  }

  @Test
  fun `onboarding view state is stored on going next`() {
    // Prepare
    val command = navigation.navigateToAuth()
    navigation.clearRecordedCalls()
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.navigationLiveEvent.observeForever(navigationObserver)

    // Execute
    viewModel.completeOnboarding()

    // Verify
    coVerifySequence {
      onboardingRepository.getPages()
      // State was saved
      onboardingRepository.setOnboardingCompleted()
      // Navigation executed
      navigation.navigateToAuth()
      navigationObserver.onEventChanged(command)
    }
  }

  private fun getOnboardingPages() = listOf(
    OnboardingPage(
      titleResId = 1,
      descriptionResId = 2,
      iconResId = 3
    )
  )
}
