package ru.frogogo.whitelabel.ui.profile

import ru.frogogo.whitelabel.core.navigation.NavigationCommand

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
