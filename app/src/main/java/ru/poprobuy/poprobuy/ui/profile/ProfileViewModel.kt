package ru.poprobuy.poprobuy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.data.model.ui.profile.toProfileModel
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.usecase.onFailure
import ru.poprobuy.poprobuy.usecase.onSuccess
import ru.poprobuy.poprobuy.usecase.user.GetUserInfoUseCase
import ru.poprobuy.poprobuy.util.ProfileUtils

class ProfileViewModel(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val userRepository: UserRepository,
  private val authRepository: AuthRepository,
  private val navigation: ProfileNavigation,
  private val profileUtils: ProfileUtils
) : BaseViewModel() {

  private val _profileLive = MutableLiveData<ProfileUiModel>()
  val profileLive: LiveData<ProfileUiModel> get() = _profileLive.distinctUntilChanged()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive: LiveData<Boolean> get() = _isLoadingLive

  override fun onCreate() {
    viewModelScope.launch {
      // Get local user
      userRepository.getUser().whatIfNotNull(
        whatIf = { _profileLive.postValue(it.toProfileModel(profileUtils.getAboutVersionText())) },
        whatIfNot = { _isLoadingLive.postValue(true) }
      )
      // Fetch network data
      getUserInfoUseCase().onSuccess {
        _profileLive.postValue(it.toProfileModel(profileUtils.getAboutVersionText()))
      }.onFailure {
        // TODO: 21.07.2020
      }
      _isLoadingLive.postValue(false)
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
