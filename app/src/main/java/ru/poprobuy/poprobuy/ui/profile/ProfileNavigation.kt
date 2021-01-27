package ru.poprobuy.poprobuy.ui.profile

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface ProfileNavigation {
  fun navigateToReceipts(): NavigationCommand
  fun navigateToGoods(): NavigationCommand
  fun navigateToSplash(): NavigationCommand
}

class ProfileNavigationImpl : ProfileNavigation {

  override fun navigateToReceipts(): NavigationCommand =
    ProfileFragmentDirections.profileToReceipts().toCommand()

  override fun navigateToGoods(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

  override fun navigateToSplash(): NavigationCommand =
    ProfileFragmentDirections.profileToSplash().toCommand()

}
