package ru.frogogo.whitelabel.ui.auth.name

import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.util.Validators

class AuthNameViewModel(
  private val navigation: AuthNameNavigation,
) : BaseViewModel() {

  private val _nameValidationLiveEvent = LiveEvent<Int?>()
  val nameValidationLiveEvent = _nameValidationLiveEvent.asLiveData()

  fun setUserName(userName: String) {
    val nameTrimmed = userName.trim()
    d { "Setting user name - $nameTrimmed" }
    if (!validateUserName(nameTrimmed)) return

    navigation.navigateToAuthEmail(nameTrimmed).navigate()
  }

  private fun validateUserName(userName: String): Boolean {
    val error = Validators.isUserName(userName)
    _nameValidationLiveEvent.postValue(error)

    return error == null
  }
}
