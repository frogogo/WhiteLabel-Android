package ru.poprobuy.poprobuy.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.hadilq.liveevent.LiveEvent
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.mapper.toProfileModel
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.usecase.user.GetUserInfoUseCase
import ru.poprobuy.poprobuy.util.ProfileUtils
import ru.poprobuy.poprobuy.util.doOnFailure
import ru.poprobuy.poprobuy.util.doOnSuccess

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
        whatIfNot = { _isLoadingLive.value = true }
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

  fun navigateToGoods() {
    i { "Navigating to goods" }
    navigation.navigateToGoods().navigate()
  }

  fun logout() {
    i { "Logging out" }
    authRepository.logout()
    navigation.navigateToSplash().navigate()
  }

}
