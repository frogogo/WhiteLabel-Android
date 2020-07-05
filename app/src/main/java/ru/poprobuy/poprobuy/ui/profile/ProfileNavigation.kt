package ru.poprobuy.poprobuy.ui.profile

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface ProfileNavigation {
  fun navigateBack(): NavigationCommand
  fun navigateToReceipts(): NavigationCommand
  fun navigateToGoods(): NavigationCommand
}

class ProfileNavigationImpl : ProfileNavigation {

  override fun navigateBack(): NavigationCommand {
    return NavigationCommand.Back
  }

  override fun navigateToReceipts(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

  override fun navigateToGoods(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

}
