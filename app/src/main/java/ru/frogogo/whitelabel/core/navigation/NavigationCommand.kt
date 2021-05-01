package ru.frogogo.whitelabel.core.navigation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import ru.frogogo.whitelabel.MainNavigationDirections

sealed class NavigationCommand {

  abstract fun execute(controller: NavController)

  data class ByAction(val action: NavDirections, val navOptions: NavOptions? = null) : NavigationCommand() {
    override fun execute(controller: NavController) {
      controller.navigate(action, navOptions)
    }
  }

  data class ByDeepLink(val deepLink: String, val navOptions: NavOptions? = null) : NavigationCommand() {
    override fun execute(controller: NavController) {
      val uri = Uri.parse(deepLink)
      controller.navigate(uri, navOptions)
    }
  }

  data class ByWebUrl(val url: String, @StringRes val titleRes: Int) : NavigationCommand() {
    override fun execute(controller: NavController) {
      val action = MainNavigationDirections.actionGlobalWebView(url, titleRes)
      controller.navigate(action)
    }
  }

  object Back : NavigationCommand() {
    override fun execute(controller: NavController) {
      controller.navigateUp()
    }
  }
}
