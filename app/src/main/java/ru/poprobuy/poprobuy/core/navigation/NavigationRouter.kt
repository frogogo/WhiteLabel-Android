package ru.poprobuy.poprobuy.core.navigation

import androidx.navigation.NavController
import com.github.ajalt.timberkt.Timber.e

class NavigationRouter(
  private val controller: NavController,
) {

  fun navigate(command: NavigationCommand) {
    runCatching {
      command.execute(controller)
    }.onFailure { exception ->
      e(exception) { "Navigation exception" }
    }
  }

}
