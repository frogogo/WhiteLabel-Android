package ru.poprobuy.poprobuy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.util.ProfileUtils

class ProfileViewModel(
  private val navigation: ProfileNavigation,
  private val profileUtils: ProfileUtils
) : BaseViewModel() {

  private val _profileLive = MutableLiveData<ProfileUiModel>()
  val profileLive: LiveData<ProfileUiModel> get() = _profileLive

  override fun onCreate() {
    super.onCreate()
    viewModelScope.launch {
      // TODO: 05.07.2020 Real data
      val profile = ProfileUiModel(
        name = "Александр",
        email = "user@mail.com",
        phoneNumber = "+7 (985) 555-88-60",
        appVersion = profileUtils.getAboutVersionText()
      )
      _profileLive.postValue(profile)
    }
  }

  fun navigateToReceipts() {
    d { "Navigating to receipts" }
    navigation.navigateToReceipts().navigate()
  }

  fun navigateToGoods() {
    d { "Navigating to goods" }
    navigation.navigateToGoods().navigate()
  }

}
