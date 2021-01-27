package ru.poprobuy.poprobuy.extension

import androidx.navigation.NavDirections
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand

fun NavDirections.toCommand(): NavigationCommand =
  NavigationCommand.ByAction(this)
