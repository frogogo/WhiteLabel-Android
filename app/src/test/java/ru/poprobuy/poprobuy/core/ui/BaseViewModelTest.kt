package ru.poprobuy.poprobuy.core.ui

import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.ViewModelTest
import ru.poprobuy.poprobuy.core.navigation.AppNavigation
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.mockkEventObserver
import ru.poprobuy.poprobuy.onEventChanged
import ru.poprobuy.poprobuy.util.Event

@ExperimentalCoroutinesApi
class BaseViewModelTest : ViewModelTest() {

  private lateinit var viewModel: BaseViewModel

  @Before
  fun startUp() {
    viewModel = BaseViewModel()
  }

  @Test
  fun `navigate extensions calls live data`() {
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.navigationLiveEvent.observeForever(navigationObserver)

    viewModel.apply {
      AppNavigation.navigateBack().navigate()
    }

    val destination = AppNavigation.navigateBack()
    verifySequence {
      navigationObserver.onEventChanged(destination)
    }
    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(destination)
  }

  @Test
  fun `navigateBack executes proper action`() {
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.navigationLiveEvent.observeForever(navigationObserver)

    viewModel.navigateBack()

    val destination = AppNavigation.navigateBack()
    verifySequence {
      navigationObserver.onEventChanged(destination)
    }
    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(destination)
  }

  @Test
  fun `hide command executes proper action`() {
    val commandObserver = mockkEventObserver<BaseCommand>()
    viewModel.baseCommandLiveEvent.observeForever(commandObserver)

    viewModel.hideKeyboard()

    verifySequence {
      commandObserver.onEventChanged(BaseCommand.HideKeyboard)
    }
    viewModel.baseCommandLiveEvent.value shouldBeEqualTo Event(BaseCommand.HideKeyboard)
  }

}
