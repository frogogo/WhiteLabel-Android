package ru.poprobuy.poprobuy.ui.profile

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface ProfileNavigation {
  fun navigateToReceipts(): NavigationCommand
  fun navigateToGoods(): NavigationCommand
  fun navigateToSplash(): NavigationCommand
}

class ProfileNavigationImpl : ProfileNavigation {

  override fun navigateToReceipts(): NavigationCommand {
    return NavigationCommand.ById(R.id.profile_to_receipts)
  }

  override fun navigateToGoods(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

  override fun navigateToSplash(): NavigationCommand {
    return NavigationCommand.ById(R.id.profile_to_splash)
  }

}
