package ru.poprobuy.poprobuy.ui.auth.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.usecase.user.UpdateUserDetailsUseCase
import ru.poprobuy.poprobuy.util.Validators

class AuthEmailViewModel(
  private val userName: String,
  private val navigation: AuthEmailNavigation,
  private val authRepository: AuthRepository,
  private val updateUserDetailsUseCase: UpdateUserDetailsUseCase
) : BaseViewModel() {

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  private val _commandLiveEvent = LiveEvent<AuthEmailCommand>()
  val commandLiveEvent: LiveData<AuthEmailCommand> get() = _commandLiveEvent

  fun updateUserData(email: String) {
    if (!validateEmail(email)) return
    d { "Sending additional user params - name = $userName, email = $email" }

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      val result = updateUserDetailsUseCase(email, userName)
      _isLoadingLive.postValue(false)

      if (result is UseCaseResult.Success) {
        authRepository.setUserAuthorized()
        hideKeyboard()
        navigation.navigateToApp().navigate()
      } else {
        _commandLiveEvent.postValue(AuthEmailCommand.SomethingWentWrong)
      }
    }
  }

  private fun validateEmail(email: String): Boolean {
    val error = Validators.isEmail(email)
    _commandLiveEvent.postValue(AuthEmailCommand.EmailValidationResult(error))

    return error == null
  }

}
