package ru.poprobuy.poprobuy.ui.auth.email

sealed class AuthEmailCommand {
  object SomethingWentWrong : AuthEmailCommand()
  class EmailValidationResult(val errorRes: Int?) : AuthEmailCommand()
}
