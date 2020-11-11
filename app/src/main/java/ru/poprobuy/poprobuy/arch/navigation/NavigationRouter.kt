package ru.poprobuy.poprobuy.arch.navigation

import android.net.Uri
import androidx.navigation.NavController
import ru.poprobuy.poprobuy.MainNavigationDirections

class NavigationRouter(
  private val controller: NavController,
) {

  /**
   * Executes navigation [command]
   */
  fun navigate(command: NavigationCommand) {
    when (command) {
      is NavigationCommand.ById -> {
        controller.navigate(command.id)
      }
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
