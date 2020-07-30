package ru.poprobuy.poprobuy.ui.onboarding

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.ViewModelTest
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.onboarding.OnboardingPage
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository
import ru.poprobuy.poprobuy.mockkObserver

@ExperimentalCoroutinesApi
class OnboardingViewModelTest : ViewModelTest() {

  private lateinit var viewModel: OnboardingViewModel
  private val onboardingRepository: OnboardingRepository = mockk(relaxed = true)
  private val navigation: OnboardingNavigationImpl = mockk(relaxed = true)

  @Before
  fun startUp() {
    every { onboardingRepository.getPages() } returns getOnboardingPages()
    viewModel = OnboardingViewModel(onboardingRepository, navigation)
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
    val navigationObserver = mockkObserver<NavigationCommand>()
    viewModel.navigationLiveEvent.observeForever(navigationObserver)

    // Execute
    viewModel.completeOnboarding()

    // Verify
    verifyOrder {
      // State was saved
      onboardingRepository.setOnboardingCompleted()
      // Navigation executed
      navigationObserver.onChanged(navigation.navigateToAuth())
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
