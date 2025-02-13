package ru.frogogo.whitelabel.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.hadilq.liveevent.LiveEvent
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.doOnFailure
import ru.frogogo.whitelabel.core.doOnSuccess
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.mapper.toProfileModel
import ru.frogogo.whitelabel.data.model.ui.profile.ProfileUiModel
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.usecase.user.GetUserInfoUseCase
import ru.frogogo.whitelabel.util.ProfileUtils

class ProfileViewModel(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val userRepository: UserRepository,
  private val authRepository: AuthRepository,
  private val navigation: ProfileNavigation,
  private val profileUtils: ProfileUtils,
) : BaseViewModel() {

  private val _profileLive = MutableLiveData<ProfileUiModel>()
  val profileLive = _profileLive.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _errorOccurredLiveEvent = LiveEvent<Unit>()
  val errorOccurredLiveEvent = _errorOccurredLiveEvent.asLiveData()

  override fun onCreate() {
    loadProfile()
  }

  fun loadProfile() {
    viewModelScope.launch {
      // Get local user
      val user = userRepository.getUser()
      user.whatIfNotNull(
        whatIf = { _profileLive.value = it.toProfileModel(profileUtils.getAboutVersionText()) },
        whatIfNot = { _isLoadingLive.value = true },
      )

      // Fetch network data
      getUserInfoUseCase().doOnSuccess {
        _profileLive.value = it.toProfileModel(profileUtils.getAboutVersionText())
        _isLoadingLive.value = false
      }.doOnFailure {
        // Do not pass error if user retrieved from local storage
        if (user == null) _errorOccurredLiveEvent.value = Unit
      }
    }
  }

  fun navigateToReceipts() {
    i { "Navigating to receipts" }
    navigation.navigateToReceipts().navigate()
  }

  fun navigateToCoupons() {
    i { "Navigating to goods" }
    navigation.navigateToCoupons().navigate()
  }

  fun logout() {
    i { "Logging out" }
    authRepository.logout()
    navigation.navigateToSplash().navigate()
  }
}
