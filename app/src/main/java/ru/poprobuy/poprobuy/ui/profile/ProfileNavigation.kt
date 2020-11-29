package ru.poprobuy.poprobuy.ui.profile

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand

interface ProfileNavigation {
  fun navigateToReceipts(): NavigationCommand
  fun navigateToGoods(): NavigationCommand
  fun navigateToSplash(): NavigationCommand
}

class ProfileNavigationImpl : ProfileNavigation {

  override fun navigateToReceipts(): NavigationCommand {
    val action = ProfileFragmentDirections.profileToReceipts()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToGoods(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

  override fun navigateToSplash(): NavigationCommand {
    val action = ProfileFragmentDirections.profileToSplash()
    return NavigationCommand.ByAction(action)
  }

}
