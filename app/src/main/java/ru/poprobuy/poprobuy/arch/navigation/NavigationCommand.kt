package ru.poprobuy.poprobuy.arch.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationCommand {
  data class ByAction(val action: NavDirections, val navOptions: NavOptions? = null) : NavigationCommand()
  data class ByDeepLink(val deepLink: String, val navOptions: NavOptions? = null) : NavigationCommand()
  data class ByWebUrl(val url: String, @StringRes val titleRes: Int) : NavigationCommand()
  object Back : NavigationCommand()
}
