package ru.poprobuy.poprobuy.ui.auth.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.util.DateUtils
import ru.poprobuy.poprobuy.util.Validators
import java.util.*

class AuthCodeConfirmationViewModel(
  private val navigation: AuthCodeConfirmationNavigation
) : BaseViewModel() {

  private val _codeValidationLiveEvent = LiveEvent<Int?>()
  val codeValidationLiveEvent: LiveData<Int?> get() = _codeValidationLiveEvent

  private val _isLoadingLive = LiveEvent<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  private val _resendCodeButtonState = MutableLiveData<Int>()
  val resendCodeButtonState: LiveData<Int> get() = _resendCodeButtonState

  private var tickerJob: Job? = null
  private var resendTime = Date(System.currentTimeMillis() + 30_000)

  override fun onStart() {
    startCodeResendCountdown()
  }

  override fun onCleared() {
    super.onCleared()
    tickerJob?.cancel()
  }

  fun navigateBack() {
    navigation.navigateBack().navigate()
  }

  fun resendConfirmationCode() {
    // TODO: 28.06.2020 Implement code resending
  }

  fun confirmPhoneNumber(confirmationCode: String) {
    d { "Confirmation code entered = $confirmationCode" }
    if (!validateConfirmationCode(confirmationCode)) return

    viewModelScope.launch {
      // TODO: 13.06.2020

      // Tmp
      _isLoadingLive.postValue(true)
      delay(1500)
      navigation.navigateToAuthName().navigate()
    }
  }

  private fun validateConfirmationCode(code: String): Boolean {
    val error = Validators.isConfirmationCode(code)
    _codeValidationLiveEvent.postValue(error)

    return error == null
  }

  private fun startCodeResendCountdown() {
    tickerJob?.cancel()
    tickerJob = viewModelScope.launch {
      repeat(Int.MAX_VALUE) {
        val difference = DateUtils.calculateSecondsDifferenceBetweenDates(Date(), resendTime)
        _resendCodeButtonState.postValue(difference)
        if (difference <= 0) cancel()
        delay(COUNTDOWN_INTERVAL)
      }
    }
  }

  companion object {
    private const val COUNTDOWN_INTERVAL = 1000L
  }

}
