package ru.poprobuy.poprobuy.ui.auth.name

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.mockkObserver

@RunWith(RobolectricTestRunner::class)
class AuthNameViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

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
    val navigationObserver = mockkObserver<NavigationCommand>()
    viewModel.apply {
      nameValidationLiveEvent.observeForever(validationResultObserver)
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.setUserName(name)

    val navigationDestination = navigation.navigateToAuthEmail(name)
    verifySequence {
      validationResultObserver.onChanged(isNull())
      navigationObserver.onChanged(navigationDestination)
    }
  }

}
