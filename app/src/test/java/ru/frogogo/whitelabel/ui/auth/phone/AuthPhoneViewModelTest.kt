package ru.frogogo.whitelabel.ui.auth.phone

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.frogogo.test.base.ViewModelTestJUnit4
import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.test.mockkEventObserver
import ru.frogogo.test.mockkObserver
import ru.frogogo.test.onEventChanged
import ru.frogogo.whitelabel.usecase.auth.RequestConfirmationCodeUseCase
import ru.frogogo.whitelabel.usecase.auth.RequestConfirmationResult

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AuthPhoneViewModelTest : ViewModelTestJUnit4() {

  private lateinit var viewModel: AuthPhoneViewModel

  private val requestConfirmationCodeUseCase: RequestConfirmationCodeUseCase = mockk(relaxed = true)
  private val navigation: AuthPhoneNavigation = mockk(relaxed = true)

  @Before
  fun setUp() {
    viewModel = AuthPhoneViewModel(
      showLogoutDialog = false,
      navigation = navigation,
      requestConfirmationCodeUseCase = requestConfirmationCodeUseCase
    )
  }

  @After
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `viewModel should show logout dialog`() {
    val viewModel = AuthPhoneViewModel(true, navigation, requestConfirmationCodeUseCase)
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    viewModel.commandLiveEvent.observeForever(commandsObserver)

    viewModel.onCreate()

    coVerifySequence {
      commandsObserver.onChanged(AuthPhoneCommand.ShowLogoutDialog)
    }
  }

  @Test
  fun `viewModel shouldn't show logout dialog`() {
    val viewModel = AuthPhoneViewModel(false, navigation, requestConfirmationCodeUseCase)
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    viewModel.commandLiveEvent.observeForever(commandsObserver)

    viewModel.onCreate()

    confirmVerified(commandsObserver, requestConfirmationCodeUseCase, navigation)
  }

  @Test
  fun `viewModel shouldn't show logout dialog second time`() {
    val viewModel = AuthPhoneViewModel(true, navigation, requestConfirmationCodeUseCase)
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    viewModel.commandLiveEvent.observeForever(commandsObserver)

    viewModel.onCreate()
    viewModel.onCreate()

    coVerifySequence {
      commandsObserver.onChanged(AuthPhoneCommand.ShowLogoutDialog)
    }
  }

  @Test
  fun `view model shows phone number validation error when requested`() = runBlockingTest {
    viewModel.requestCode("", true)

    viewModel.commandLiveEvent.value shouldBeInstanceOf AuthPhoneCommand.PhoneValidationResult::class
    // Verify that use case was not called as phone number is incorrect
    verify {
      requestConfirmationCodeUseCase wasNot Called
      navigation wasNot Called
    }
  }

  @Test
  fun `view model doesn't show phone number validation error when not requested`() = runBlockingTest {
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    viewModel.commandLiveEvent.observeForever(commandsObserver)

    viewModel.requestCode("", false)

    // Verify that use case was not called as phone number is incorrect
    verify {
      requestConfirmationCodeUseCase wasNot Called
      navigation wasNot Called
    }

    coVerifySequence {
      commandsObserver.onChanged(AuthPhoneCommand.ClearError)
    }
  }

  @Test
  fun `view model handles success result`() {
    coEvery { requestConfirmationCodeUseCase(any()) } returns RequestConfirmationResult.Success(60)
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    val isLoadingObserver = mockkObserver<Boolean>()
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.apply {
      commandLiveEvent.observeForever(commandsObserver)
      isLoadingLive.observeForever(isLoadingObserver)
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.requestCode(VALID_PHONE_NUMBER)

    val navigationDestination = navigation.navigateToAuthCodeConfirmation(VALID_PHONE_NUMBER, 60)
    coVerifySequence {
      commandsObserver.onChanged(AuthPhoneCommand.ClearError)
      isLoadingObserver.onChanged(true)
      requestConfirmationCodeUseCase(any())
      isLoadingObserver.onChanged(false)
      navigationObserver.onEventChanged(navigationDestination)
    }
  }

  @Test
  fun `view model handles too many requests result`() {
    coEvery { requestConfirmationCodeUseCase(any()) } returns RequestConfirmationResult.TooManyRequests
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    val isLoadingObserver = mockkObserver<Boolean>()
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.apply {
      commandLiveEvent.observeForever(commandsObserver)
      isLoadingLive.observeForever(isLoadingObserver)
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.requestCode(VALID_PHONE_NUMBER)

    verify {
      navigationObserver wasNot Called
      navigation wasNot Called
    }
    coVerifySequence {
      commandsObserver.onChanged(AuthPhoneCommand.ClearError)
      isLoadingObserver.onChanged(true)
      requestConfirmationCodeUseCase(any())
      isLoadingObserver.onChanged(false)
      commandsObserver.onChanged(AuthPhoneCommand.TooManyRequestsError)
    }
  }

  @Test
  fun `view model handles error result`() {
    coEvery { requestConfirmationCodeUseCase(any()) } returns RequestConfirmationResult.Error
    val commandsObserver = mockkObserver<AuthPhoneCommand>()
    val isLoadingObserver = mockkObserver<Boolean>()
    val navigationObserver = mockkEventObserver<NavigationCommand>()
    viewModel.apply {
      commandLiveEvent.observeForever(commandsObserver)
      isLoadingLive.observeForever(isLoadingObserver)
      navigationLiveEvent.observeForever(navigationObserver)
    }

    viewModel.requestCode(VALID_PHONE_NUMBER)

    verify {
      navigationObserver wasNot Called
      navigation wasNot Called
    }
    coVerifySequence {
      commandsObserver.onChanged(AuthPhoneCommand.ClearError)
      isLoadingObserver.onChanged(true)
      requestConfirmationCodeUseCase(any())
      isLoadingObserver.onChanged(false)
      commandsObserver.onChanged(AuthPhoneCommand.SomethingWentWrong)
    }
  }

  companion object {
    const val VALID_PHONE_NUMBER = "+79162795600"
  }
}
