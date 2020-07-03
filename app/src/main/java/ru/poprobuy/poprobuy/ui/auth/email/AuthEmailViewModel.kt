package ru.poprobuy.poprobuy.ui.auth.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.Validators

class AuthEmailViewModel(
  private val userName: String,
  private val navigation: AuthEmailNavigation,
  private val authRepository: AuthRepository
) : BaseViewModel() {

  private val _emailValidationLiveEvent = LiveEvent<Int?>()
  val emailValidationLiveEvent: LiveData<Int?> get() = _emailValidationLiveEvent

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  private val _hideKeyboardLiveEvent = MutableLiveData<Unit>()
  val hideKeyboardLiveEvent: LiveData<Unit> get() = _hideKeyboardLiveEvent

  fun updateUserData(email: String) {
    if (!validateEmail(email)) return
    d { "Sending additional user params - name = $userName, email = $email" }

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      delay(1000)
      authRepository.setUserAuthorized()
      _hideKeyboardLiveEvent.postValue(Unit)
      navigation.navigateToApp().navigate()
    }
  }

  private fun validateEmail(email: String): Boolean {
    val error = Validators.isEmail(email)
    _emailValidationLiveEvent.postValue(error)

    return error == null
  }

}
