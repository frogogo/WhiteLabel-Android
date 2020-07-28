package ru.poprobuy.poprobuy.ui.auth.code

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.CoroutinesTestRule
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.arch.ui.BaseCommand
import ru.poprobuy.poprobuy.mockkObserver
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationResult
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationCodeUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationResult
import ru.poprobuy.poprobuy.util.OtpRequestDisabler

@ExperimentalCoroutinesApi
class AuthCodeViewModelTest {

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: AuthCodeViewModel
  private val navigation: AuthCodeConfirmationNavigation = mockk(relaxed = true)
  private val authenticationUseCase: AuthenticationUseCase = mockk(relaxed = true)
  private val requestConfirmationCodeUseCase: RequestConfirmationCodeUseCase = mockk(relaxed = true)
  private val otpRequestDisabler: OtpRequestDisabler = mockk(relaxed = true)

  private val baseCommandsObserver = mockkObserver<BaseCommand>()
  private val navigationObserver = mockkObserver<NavigationCommand>()
  private val commandsObserver = mockkObserver<AuthCodeCommand>()
  private val isLoadingObserver = mockkObserver<Boolean>()
  private val isResendingCodeObserver = mockkObserver<Boolean>()

  @Before
  fun startUp() {
    clearAllMocks()
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
      navigationObserver.onChanged(navigationDestination)
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
      baseCommandsObserver.onChanged(BaseCommand.HideKeyboard)
      navigationObserver.onChanged(navigationDestination)
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
