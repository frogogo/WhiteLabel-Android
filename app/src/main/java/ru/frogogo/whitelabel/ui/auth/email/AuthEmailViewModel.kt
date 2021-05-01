package ru.frogogo.whitelabel.ui.auth.email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.usecase.user.UpdateUserDetailsUseCase
import ru.frogogo.whitelabel.util.Validators

class AuthEmailViewModel(
  private val userName: String,
  private val navigation: AuthEmailNavigation,
  private val authRepository: AuthRepository,
  private val updateUserDetailsUseCase: UpdateUserDetailsUseCase,
) : BaseViewModel() {

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _commandLiveEvent = LiveEvent<AuthEmailCommand>()
  val commandLiveEvent = _commandLiveEvent.asLiveData()

  fun updateUserData(email: String) {
    if (!validateEmail(email)) return
    d { "Sending additional user params - name = $userName, email = $email" }

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      updateUserDetailsUseCase(email, userName).handle(
        onSuccess = {
          hideKeyboard()
          authRepository.setUserAuthorized()
          navigation.navigateToApp().navigate()
        },
        onFailure = { _commandLiveEvent.postValue(AuthEmailCommand.SomethingWentWrong) }
      )
      _isLoadingLive.postValue(false)
    }
  }

  private fun validateEmail(email: String): Boolean {
    val error = Validators.isEmail(email)
    _commandLiveEvent.postValue(AuthEmailCommand.EmailValidationResult(error))

    return error == null
  }
}
