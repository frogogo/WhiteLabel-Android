package ru.poprobuy.poprobuy.ui.auth.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationResult
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationUseCase
import ru.poprobuy.poprobuy.util.DateUtils
import ru.poprobuy.poprobuy.util.Validators
import java.util.*

class AuthCodeViewModel(
  private val phoneNumber: String,
  private val navigation: AuthCodeConfirmationNavigation,
  private val authenticationUseCase: AuthenticationUseCase
) : BaseViewModel() {

  private val _commandLiveEvent = LiveEvent<AuthCodeCommand>()
  val commandLiveEvent: LiveData<AuthCodeCommand> get() = _commandLiveEvent

  private val _isLoadingLive = LiveEvent<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  private val _resendCodeButtonState = MutableLiveData<Int>()
  val resendCodeButtonState: LiveData<Int> get() = _resendCodeButtonState

  private var timerJob: Job? = null
  private var resendTime = Date(System.currentTimeMillis() + 30_000)

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

  fun resendConfirmationCode() {
    // TODO: 28.06.2020 Implement code resending
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
          if (result.response.isNew) {
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
      val ticker = ticker(COUNTDOWN_INTERVAL, COUNTDOWN_START_DELAY)
      for (ignored in ticker) {
        val difference = DateUtils.calculateSecondsDifferenceBetweenDates(Date(), resendTime)
        _resendCodeButtonState.postValue(difference)
        if (difference <= 0) cancel()
      }
    }
  }

  companion object {
    private const val COUNTDOWN_INTERVAL = 1000L
    private const val COUNTDOWN_START_DELAY = 0L
  }

}
