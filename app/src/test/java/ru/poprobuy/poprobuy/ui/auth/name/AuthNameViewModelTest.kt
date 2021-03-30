package ru.poprobuy.poprobuy.ui.auth.name

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.poprobuy.test.base.ViewModelTestJUnit4
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.test.mockkEventObserver
import ru.poprobuy.test.mockkObserver
import ru.poprobuy.test.onEventChanged

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AuthNameViewModelTest : ViewModelTestJUnit4() {

  private lateinit var viewModel: AuthNameViewModel
  private val navigation: AuthNameNavigation = mockk(relaxed = true)

  @Before
  fun setUp() {
    viewModel = AuthNameViewModel(navigation)
  }

  @Test
  fun `view model shows name validation error`() {
    val validationResultObserver = mockkObserver<Int?>()
    viewModel.nameValidationLiveEvent.observeForever(validationResultObserver)

    viewModel.setUserName("")

    verify {
      navigation wasNot Called
      validationResultObserver.onChanged(any())
    }
  }

  @Test
  fun `view model navigates next`() {
    val name = "Alexey"
    val validationResultObserver = mockkObserver<Int?>()
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.apply {
      nameValidationLiveEvent.observeForever(validationResultObserver)
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.setUserName(name)

    val navigationDestination = navigation.navigateToAuthEmail(name)
    verifySequence {
      validationResultObserver.onChanged(isNull())
      navigationObserver.onEventChanged(navigationDestination)
    }
  }
}
