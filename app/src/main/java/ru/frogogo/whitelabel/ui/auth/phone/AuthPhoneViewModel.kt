package ru.frogogo.whitelabel.ui.auth.phone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.hadilq.liveevent.LiveEvent
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.extension.getUnformattedPhoneNumber
import ru.frogogo.whitelabel.usecase.auth.RequestConfirmationCodeUseCase
import ru.frogogo.whitelabel.usecase.auth.RequestConfirmationResult
import ru.frogogo.whitelabel.util.PhoneUtils
import ru.frogogo.whitelabel.util.Validators

class AuthPhoneViewModel(
  private val showLogoutDialog: Boolean,
  private val navigation: AuthPhoneNavigation,
  private val requestConfirmationCodeUseCase: RequestConfirmationCodeUseCase,
) : BaseViewModel() {

  private val _commandLiveEvent = LiveEvent<AuthPhoneCommand>()
  val commandLiveEvent = _commandLiveEvent.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private var logoutDialogShown = false

  override fun onCreate() {
    super.onCreate()
    if (showLogoutDialog && !logoutDialogShown) {
      logoutDialogShown = true
      _commandLiveEvent.postValue(AuthPhoneCommand.ShowLogoutDialog)
    }
  }

  fun requestCode(phoneNumber: String, showValidationError: Boolean = false) {
    _commandLiveEvent.postValue(AuthPhoneCommand.ClearError)

    // Remove formatting
    val phoneNumberUnformatted = phoneNumber.getUnformattedPhoneNumber()
    d { "Entered phone - $phoneNumber, unformatted - $phoneNumberUnformatted" }
    if (!validatePhone(phoneNumberUnformatted, showValidationError)) return
    val phoneNumberPrefixed = PhoneUtils.addPrefix(phoneNumberUnformatted)

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      val result = requestConfirmationCodeUseCase(phoneNumberPrefixed)
      _isLoadingLive.postValue(false)

      @Exhaustive
      when (result) {
        is RequestConfirmationResult.Success -> {
          navigation.navigateToAuthCodeConfirmation(phoneNumberPrefixed, result.refreshRate).navigate()
        }
        RequestConfirmationResult.TooManyRequests -> {
          _commandLiveEvent.postValue(AuthPhoneCommand.TooManyRequestsError)
        }
        RequestConfirmationResult.Error -> {
          _commandLiveEvent.postValue(AuthPhoneCommand.SomethingWentWrong)
        }
      }
    }
  }

  private fun validatePhone(phoneNumber: String, showValidationError: Boolean = false): Boolean {
    val error = Validators.isPhone(phoneNumber)
    if (showValidationError) {
      _commandLiveEvent.postValue(AuthPhoneCommand.PhoneValidationResult(error))
    }

    // Log negative result
    error.whatIfNotNull { e { "Validation failed for number - $phoneNumber" } }

    return error == null
  }
}
