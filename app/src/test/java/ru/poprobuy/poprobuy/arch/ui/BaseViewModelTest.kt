package ru.poprobuy.poprobuy.arch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.verifySequence
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.arch.navigation.AppNavigation
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.mockkObserver

class BaseViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: BaseViewModel

  @Before
  fun startUp() {
    viewModel = BaseViewModel()
  }

  @Test
  fun `navigate extensions calls live data`() {
    val navigationObserver = mockkObserver<NavigationCommand>()
    viewModel.apply {
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.apply {
      AppNavigation.navigateBack().navigate()
    }

    val destination = AppNavigation.navigateBack()
    verifySequence {
      navigationObserver.onChanged(destination)
    }
    viewModel.navigationLiveEvent.value shouldBeEqualTo destination
  }

  @Test
  fun `navigateBack executes proper action`() {
    val navigationObserver = mockkObserver<NavigationCommand>()
    viewModel.apply {
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.navigateBack()

    val destination = AppNavigation.navigateBack()
    verifySequence {
      navigationObserver.onChanged(destination)
    }
    viewModel.navigationLiveEvent.value shouldBeEqualTo destination
  }

}
