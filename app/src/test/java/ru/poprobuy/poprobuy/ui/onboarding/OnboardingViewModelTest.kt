package ru.poprobuy.poprobuy.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.onboarding.OnboardingPage
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository

class OnboardingViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var onboardingViewModel: OnboardingViewModel
  private val onboardingRepository: OnboardingRepository = mockk(relaxed = true)
  private val onboardingNavigation: OnboardingNavigationImpl = mockk(relaxed = true)

  @Before
  fun startUp() {
    every { onboardingRepository.getPages() } returns getOnboardingPages()
    onboardingViewModel = OnboardingViewModel(onboardingRepository, onboardingNavigation)
  }

  @Test
  fun `posts proper list to LiveData`() {
    val dataObserver = mockk<Observer<List<RecyclerViewItem>>>(relaxed = true)
    onboardingViewModel.pagesLive.observeForever(dataObserver)

    verify {
      dataObserver.onChanged(getOnboardingPages())
    }
  }

  @Test
  fun `onboarding view state is stored on going next`() {
    // Prepare
    val navigationObserver = mockk<Observer<NavigationCommand>>(relaxed = true)
    onboardingViewModel.navigationLiveEvent.observeForever(navigationObserver)

    // Execute
    onboardingViewModel.completeOnboarding()

    // Verify
    verifyOrder {
      // State was saved
      onboardingRepository.setOnboardingCompleted()
      // Navigation executed
      navigationObserver.onChanged(onboardingNavigation.navigateToAuth())
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
