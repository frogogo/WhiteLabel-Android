package ru.poprobuy.poprobuy.ui

import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.usecase.ClearUserDataUseCase

class MainViewModel(
  private val navigation: MainNavigation,
  private val clearUserDataUseCase: ClearUserDataUseCase,
) : BaseViewModel() {

  fun logout() {
    clearUserDataUseCase()
    navigation.navigateToLoginDestructive().navigate()
  }

}
