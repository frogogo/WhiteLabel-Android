package ru.poprobuy.poprobuy.arch.navigation

import android.net.Uri
import androidx.navigation.NavController

object NavigationRouter {

  /**
   * Executes navigation [command] for given navigation [controller]
   */
  fun navigate(controller: NavController, command: NavigationCommand) {
    when (command) {
      is NavigationCommand.ById -> controller.navigate(command.id)
      is NavigationCommand.ByAction -> controller.navigate(command.action, command.navOptions)
      is NavigationCommand.ByDeepLink -> {
        val uri = Uri.parse(command.deepLink)
        controller.navigate(uri, command.navOptions)
      }
      NavigationCommand.Back -> controller.navigateUp()
    }
  }

}
