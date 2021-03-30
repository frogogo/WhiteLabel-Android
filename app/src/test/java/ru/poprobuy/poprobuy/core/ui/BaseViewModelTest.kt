package ru.poprobuy.poprobuy.core.ui

import io.mockk.clearAllMocks
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.ViewModelTestJUnit5
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.poprobuy.core.navigation.AppNavigation
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.test.mockkEventObserver
import ru.poprobuy.test.onEventChanged

@ExperimentalCoroutinesApi
class BaseViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: BaseViewModel

  @BeforeEach
  fun startUp() {
    viewModel = BaseViewModel()
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
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
