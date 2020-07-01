package ru.poprobuy.poprobuy.ui.auth.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.util.Validators

class AuthEmailViewModel(
  private val userName: String
) : BaseViewModel() {

  private val _emailValidationLiveEvent = LiveEvent<Int?>()
  val emailValidationLiveEvent: LiveData<Int?> get() = _emailValidationLiveEvent

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  fun updateUserData(email: String) {
    if (!validateEmail(email)) return
    d { "Sending additional user params - name = $userName, email = $email" }

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
    }
  }

  private fun validateEmail(email: String): Boolean {
    val error = Validators.isEmail(email)
    _emailValidationLiveEvent.postValue(error)

    return error == null
  }

}
