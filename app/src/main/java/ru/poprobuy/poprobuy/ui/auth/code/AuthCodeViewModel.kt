package ru.poprobuy.poprobuy.ui.auth.code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationResult
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationCodeUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationResult
import ru.poprobuy.poprobuy.util.OtpRequestDisabler
import ru.poprobuy.poprobuy.util.Validators

class AuthCodeViewModel(
  private val phoneNumber: String,
  private val navigation: AuthCodeConfirmationNavigation,
  private val authenticationUseCase: AuthenticationUseCase,
  private val requestConfirmationCodeUseCase: RequestConfirmationCodeUseCase,
  private val otpRequestDisabler: OtpRequestDisabler,
) : BaseViewModel() {

  private val _commandLiveEvent = LiveEvent<AuthCodeCommand>()
  val commandLiveEvent = _commandLiveEvent.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _isResendingCodeLive = MutableLiveData<Boolean>()
  val isResendingCodeLive = _isResendingCodeLive.asLiveData()

  private val _resendCodeTimeRemainingLive = MutableLiveData<Int>()
  val resendCodeTimeRemainingLive = _resendCodeTimeRemainingLive

  private var timerJob: Job? = null

  override fun onStart() {
    startCodeResendCountdown()
  }

  override fun onStop() {
    super.onStop()
    timerJob?.cancel()
    d { "Timer job canceled" }
  }

  override fun onCleared() {
    super.onCleared()
    timerJob?.cancel()
    d { "Timer job canceled" }
  }

  fun setResendDelay(delay: Int) {
    otpRequestDisabler.submitDelay(delay)
    startCodeResendCountdown()
  }

  fun resendConfirmationCode() {
    viewModelScope.launch {
      _isResendingCodeLive.postValue(true)
      val result = requestConfirmationCodeUseCase(phoneNumber)
      _isResendingCodeLive.postValue(false)

      if (result is RequestConfirmationResult.Success) {
        setResendDelay(result.refreshRate)
      } else {
        _commandLiveEvent.postValue(AuthCodeCommand.CodeResendError)
      }
    }
  }

  fun confirmPhoneNumber(confirmationCode: String) {
    d { "Confirmation code entered = $confirmationCode" }
    if (!validateConfirmationCode(confirmationCode)) return

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      val result = authenticationUseCase(phoneNumber, confirmationCode)
      _isLoadingLive.postValue(false)

      when (result) {
        is AuthenticationResult.Success -> {
          if (result.isNewUser) {
            navigation.navigateToAuthName().navigate()
          } else {
            hideKeyboard()
            navigation.navigateToApp().navigate()
          }
        }
        AuthenticationResult.Error -> _commandLiveEvent.postValue(AuthCodeCommand.SomethingWentWrong)
        AuthenticationResult.NotFound -> _commandLiveEvent.postValue(AuthCodeCommand.UserNotFoundError)
      }
    }
  }

  private fun validateConfirmationCode(code: String): Boolean {
    val error = Validators.isConfirmationCode(code)
    _commandLiveEvent.postValue(AuthCodeCommand.CodeValidationResult(error))

    return error == null
  }

  private fun startCodeResendCountdown() {
    d { "Starting timer" }
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      otpRequestDisabler.disabledForSecondsFlow.collect { timeRemaining ->
        _resendCodeTimeRemainingLive.postValue(timeRemaining ?: 0)
        if (timeRemaining == null) cancel()
      }
    }
  }

}
