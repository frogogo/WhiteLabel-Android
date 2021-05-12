package ru.frogogo.whitelabel.ui

import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.usecase.ClearUserDataUseCase

class MainViewModel(
  private val navigation: MainNavigation,
  private val clearUserDataUseCase: ClearUserDataUseCase,
) : BaseViewModel() {

  fun logout() {
    clearUserDataUseCase()
    navigation.navigateToLoginDestructive().navigate()
  }
}
