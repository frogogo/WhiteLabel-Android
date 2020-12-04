package ru.poprobuy.poprobuy.core.ui

sealed class BaseCommand {
  object HideKeyboard : BaseCommand()
}
