package ru.poprobuy.poprobuy.ui.auth.code

sealed class AuthCodeCommand {
  object ClearError : AuthCodeCommand()
  object UserNotFoundError : AuthCodeCommand()
  object SomethingWentWrong : AuthCodeCommand()
  data class CodeValidationResult(val errorRes: Int?) : AuthCodeCommand()
}
