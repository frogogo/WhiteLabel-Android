package ru.poprobuy.poprobuy.arch.ui

sealed class BaseCommand {
  object HideKeyboard : BaseCommand()
}
