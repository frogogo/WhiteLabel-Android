package ru.poprobuy.poprobuy.arch.navigation

import android.net.Uri
import androidx.navigation.NavController
import com.github.ajalt.timberkt.Timber.e
import ru.poprobuy.poprobuy.MainNavigationDirections

class NavigationRouter(
  private val controller: NavController,
) {

  fun navigate(command: NavigationCommand) {
    runCatching {
      executeCommand(command)
    }.onFailure { exception ->
      e(exception) { "Navigation exception" }
    }
  }

  private fun executeCommand(command: NavigationCommand) {
    when (command) {
      is NavigationCommand.ByAction -> {
        controller.navigate(command.action, command.navOptions)
      }
      is NavigationCommand.ByDeepLink -> {
        val uri = Uri.parse(command.deepLink)
        controller.navigate(uri, command.navOptions)
      }
      is NavigationCommand.ByWebUrl -> {
        val action = MainNavigationDirections.actionGlobalWebView(command.url, command.titleRes)
        controller.navigate(action)
      }
      NavigationCommand.Back -> {
        controller.navigateUp()
      }
    }
  }

}
