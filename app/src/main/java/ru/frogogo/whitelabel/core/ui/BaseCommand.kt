package ru.frogogo.whitelabel.core.ui

sealed class BaseCommand {
  object HideKeyboard : BaseCommand()
}
