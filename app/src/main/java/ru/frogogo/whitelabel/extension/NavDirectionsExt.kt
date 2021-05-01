package ru.frogogo.whitelabel.extension

import androidx.navigation.NavDirections
import ru.frogogo.whitelabel.core.navigation.NavigationCommand

fun NavDirections.toCommand(): NavigationCommand =
  NavigationCommand.ByAction(this)
