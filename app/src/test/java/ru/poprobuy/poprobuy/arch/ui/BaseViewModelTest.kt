package ru.poprobuy.poprobuy.arch.ui

import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.ViewModelTest
import ru.poprobuy.poprobuy.arch.navigation.AppNavigation
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.mockkObserver

@ExperimentalCoroutinesApi
class BaseViewModelTest : ViewModelTest() {

  private lateinit var viewModel: BaseViewModel

  @Before
  fun startUp() {
    viewModel = BaseViewModel()
  }

  @Test
  fun `navigate extensions calls live data`() {
    val navigationObserver = mockkObserver<NavigationCommand>()
    viewModel.navigationLiveEvent.observeForever(navigationObserver)

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
    viewModel.navigationLiveEvent.observeForever(navigationObserver)

    viewModel.navigateBack()

    val destination = AppNavigation.navigateBack()
    verifySequence {
      navigationObserver.onChanged(destination)
    }
    viewModel.navigationLiveEvent.value shouldBeEqualTo destination
  }

  @Test
  fun `hide command executes proper action`() {
    val commandObserver = mockkObserver<BaseCommand>()
    viewModel.baseCommandLiveEvent.observeForever(commandObserver)

    viewModel.hideKeyboard()

    verifySequence {
      commandObserver.onChanged(BaseCommand.HideKeyboard)
    }
    viewModel.baseCommandLiveEvent.value shouldBeEqualTo BaseCommand.HideKeyboard
  }

}
