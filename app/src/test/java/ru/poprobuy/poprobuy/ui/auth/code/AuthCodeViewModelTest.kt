package ru.poprobuy.poprobuy.ui.auth.code

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.ViewModelTestJUnit5
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.core.ui.BaseCommand
import ru.poprobuy.test.mockkEventObserver
import ru.poprobuy.test.mockkObserver
import ru.poprobuy.test.onEventChanged
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationResult
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationCodeUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationResult
import ru.poprobuy.poprobuy.util.OtpRequestDisabler

@ExperimentalCoroutinesApi
class AuthCodeViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: AuthCodeViewModel

  private val navigation: AuthCodeConfirmationNavigation = mockk(relaxed = true)
  private val authenticationUseCase: AuthenticationUseCase = mockk(relaxed = true)
  private val requestConfirmationCodeUseCase: RequestConfirmationCodeUseCase = mockk(relaxed = true)
  private val otpRequestDisabler: OtpRequestDisabler = mockk(relaxed = true)

  private val baseCommandsObserver = mockkEventObserver<BaseCommand>()
  private val navigationObserver = mockkEventObserver<NavigationCommand>()
  private val commandsObserver = mockkObserver<AuthCodeCommand>()
  private val isLoadingObserver = mockkObserver<Boolean>()
  private val isResendingCodeObserver = mockkObserver<Boolean>()

  @BeforeEach
  fun startUp() {
    viewModel = AuthCodeViewModel(
      phoneNumber = PHONE_NUMBER,
      navigation = navigation,
      authenticationUseCase = authenticationUseCase,
      requestConfirmationCodeUseCase = requestConfirmationCodeUseCase,
      otpRequestDisabler = otpRequestDisabler
    ).apply {
      navigationLiveEvent.observeForever(navigationObserver)
      baseCommandLiveEvent.observeForever(baseCommandsObserver)
      isLoadingLive.observeForever(isLoadingObserver)
      isResendingCodeLive.observeForever(isResendingCodeObserver)
      commandLiveEvent.observeForever(commandsObserver)
    }
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `verify that setting resend delay calls disabler`() {
    viewModel.setResendDelay(30)

    verify {
      otpRequestDisabler.submitDelay(30)
    }
  }

  @Test
  fun `verify flow when code resend succeed`() = runBlockingTest {
    coEvery { requestConfirmationCodeUseCase(any()) } returns RequestConfirmationResult.Success(30)

    viewModel.resendConfirmationCode()

    verify {
      authenticationUseCase wasNot Called
      navigation wasNot Called
    }

    coVerifySequence {
      isResendingCodeObserver.onChanged(true)
      requestConfirmationCodeUseCase(any())
      isResendingCodeObserver.onChanged(false)
    }
  }

  @Test
  fun `verify flow when code resend failed`() = runBlockingTest {
    coEvery { requestConfirmationCodeUseCase(any()) } returns RequestConfirmationResult.Error

    viewModel.resendConfirmationCode()

    verify {
      authenticationUseCase wasNot Called
      navigation wasNot Called
      otpRequestDisabler wasNot Called
    }

    coVerifySequence {
      isResendingCodeObserver.onChanged(true)
      requestConfirmationCodeUseCase(any())
      isResendingCodeObserver.onChanged(false)
      commandsObserver.onChanged(AuthCodeCommand.CodeResendError)
    }
  }

  @Test
  fun `verify flow when code confirmation succeed with new user`() = runBlockingTest {
    coEvery { authenticationUseCase(any(), any()) } returns AuthenticationResult.Success(true)

    viewModel.confirmPhoneNumber(CONFIRMATION_CODE)

    val navigationDestination = navigation.navigateToAuthName()
    coVerifySequence {
      commandsObserver.onChanged(AuthCodeCommand.CodeValidationResult(null))
      isLoadingObserver.onChanged(true)
      authenticationUseCase(PHONE_NUMBER, CONFIRMATION_CODE)
      isLoadingObserver.onChanged(false)
      navigationObserver.onEventChanged(navigationDestination)
    }
  }

  @Test
  fun `verify flow when code confirmation succeed with old user`() = runBlockingTest {
    coEvery { authenticationUseCase(any(), any()) } returns AuthenticationResult.Success(false)

    viewModel.confirmPhoneNumber(CONFIRMATION_CODE)

    val navigationDestination = navigation.navigateToApp()
    coVerifySequence {
      commandsObserver.onChanged(AuthCodeCommand.CodeValidationResult(null))
      isLoadingObserver.onChanged(true)
      authenticationUseCase(PHONE_NUMBER, CONFIRMATION_CODE)
      isLoadingObserver.onChanged(false)
      baseCommandsObserver.onEventChanged(BaseCommand.HideKeyboard)
      navigationObserver.onEventChanged(navigationDestination)
    }
  }

  @Test
  fun `verify flow when code confirmation wrong code`() = runBlockingTest {
    coEvery { authenticationUseCase(any(), any()) } returns AuthenticationResult.NotFound

    viewModel.confirmPhoneNumber(CONFIRMATION_CODE)

    coVerifySequence {
      commandsObserver.onChanged(AuthCodeCommand.CodeValidationResult(null))
      isLoadingObserver.onChanged(true)
      authenticationUseCase(PHONE_NUMBER, CONFIRMATION_CODE)
      isLoadingObserver.onChanged(false)
      commandsObserver.onChanged(AuthCodeCommand.UserNotFoundError)
    }
  }

  @Test
  fun `verify flow when code confirmation went wrong`() = runBlockingTest {
    coEvery { authenticationUseCase(any(), any()) } returns AuthenticationResult.Error

    viewModel.confirmPhoneNumber(CONFIRMATION_CODE)

    coVerifySequence {
      commandsObserver.onChanged(AuthCodeCommand.CodeValidationResult(null))
      isLoadingObserver.onChanged(true)
      authenticationUseCase(PHONE_NUMBER, CONFIRMATION_CODE)
      isLoadingObserver.onChanged(false)
      commandsObserver.onChanged(AuthCodeCommand.SomethingWentWrong)
    }
  }

  companion object {
    private const val PHONE_NUMBER = "+79162795600"
    private const val CONFIRMATION_CODE = "1233"
  }

}
