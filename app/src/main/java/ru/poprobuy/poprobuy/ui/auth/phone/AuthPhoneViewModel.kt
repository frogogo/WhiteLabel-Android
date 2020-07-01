package ru.poprobuy.poprobuy.ui.auth.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.util.Validators

class AuthPhoneViewModel(
  private val navigation: AuthPhoneNavigation
) : BaseViewModel() {

  private val _phoneValidationLiveEvent = LiveEvent<Int?>()
  val phoneValidationLiveEvent: LiveData<Int?> get() = _phoneValidationLiveEvent

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  private var phoneNumber = ""

  fun setPhoneNumber(phoneNumber: String) {
    d { "Entered phone - $phoneNumber" }
    this.phoneNumber = phoneNumber
    requestCode()
  }

  fun requestCode() {
    if (!validatePhone(phoneNumber)) return
    // TODO: 10.06.2020

    // Temp
    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      delay(1500)
      _isLoadingLive.postValue(false)
      navigation.navigateToAuthCodeConfirmation(phoneNumber).navigate()
    }
  }

  private fun validatePhone(phoneNumber: String): Boolean {
    val error = Validators.isPhone(phoneNumber)
    _phoneValidationLiveEvent.postValue(error)

    return error == null
  }

}
