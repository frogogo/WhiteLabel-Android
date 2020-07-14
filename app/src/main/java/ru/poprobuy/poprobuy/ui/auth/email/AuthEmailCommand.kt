package ru.poprobuy.poprobuy.ui.auth.email

sealed class AuthEmailCommand {
  object HideKeyboard : AuthEmailCommand()
}
