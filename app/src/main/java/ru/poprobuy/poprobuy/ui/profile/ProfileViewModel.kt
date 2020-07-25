package ru.poprobuy.poprobuy.ui.profile

import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseAction
import ru.poprobuy.poprobuy.arch.ui.BaseViewState
import ru.poprobuy.poprobuy.arch.ui.StateViewModel
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
) : StateViewModel<ProfileViewModel.ViewState, ProfileViewModel.Action>(ViewState()) {

  override fun onCreate() {
    loadProfile()
  }

  override fun onReduceState(action: Action): ViewState = when (action) {
    is Action.ProfileLoading -> state.copy(
      // Do not show loading if profile was already passed
      isLoading = action.isLoading && state.profile == null,
      isError = false
    )
    is Action.ProfileLoadSuccess -> state.copy(
      isLoading = false,
      isError = false,
      profile = action.profile
    )
    Action.ProfileLoadFailure -> state.copy(
      isLoading = false,
      // Do not show error if profile was already passed
      isError = state.profile == null
    )
  }

  fun loadProfile() {
    viewModelScope.launch {
      // Get local user
      userRepository.getUser().whatIfNotNull(
        whatIf = { sendAction(Action.ProfileLoadSuccess(it.toProfileModel(profileUtils.getAboutVersionText()))) },
        whatIfNot = { sendAction(Action.ProfileLoading(true)) }
      )

      // Fetch network data
      getUserInfoUseCase().onSuccess {
        sendAction(Action.ProfileLoadSuccess(it.toProfileModel(profileUtils.getAboutVersionText())))
      }.onFailure {
        sendAction(Action.ProfileLoadFailure)
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

  data class ViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val profile: ProfileUiModel? = null
  ) : BaseViewState

  sealed class Action : BaseAction {
    data class ProfileLoading(val isLoading: Boolean) : Action()
    data class ProfileLoadSuccess(val profile: ProfileUiModel) : Action()
    object ProfileLoadFailure : Action()
  }

}
