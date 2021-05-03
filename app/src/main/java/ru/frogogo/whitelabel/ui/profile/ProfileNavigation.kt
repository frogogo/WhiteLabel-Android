package ru.frogogo.whitelabel.ui.profile

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface ProfileNavigation {
  fun navigateToReceipts(): NavigationCommand
  fun navigateToCoupons(): NavigationCommand
  fun navigateToSplash(): NavigationCommand
}

class ProfileNavigationImpl : ProfileNavigation {

  override fun navigateToReceipts(): NavigationCommand =
    ProfileFragmentDirections.profileToReceipts().toCommand()

  override fun navigateToCoupons(): NavigationCommand=
    ProfileFragmentDirections.profileToCoupons().toCommand()

  override fun navigateToSplash(): NavigationCommand =
    ProfileFragmentDirections.profileToSplash().toCommand()
}
