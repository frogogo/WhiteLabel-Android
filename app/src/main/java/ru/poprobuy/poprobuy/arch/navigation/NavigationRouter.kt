package ru.poprobuy.poprobuy.arch.navigation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import ru.poprobuy.poprobuy.MainNavigationDirections
import ru.poprobuy.poprobuy.extension.openUrlInTabs

class NavigationRouter(
  private val activity: AppCompatActivity,
  private val controller: NavController
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
        // Try to open url in chrome tabs
        activity.openUrlInTabs(command.url) {
          // Open own WebView fragment if tabs failed
          val action = MainNavigationDirections.actionGlobalWebView(command.url, command.titleRes)
          controller.navigate(action)
        }
      }
      NavigationCommand.Back -> {
        controller.navigateUp()
      }
    }
  }

}
