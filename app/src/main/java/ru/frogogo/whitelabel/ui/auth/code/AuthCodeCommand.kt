package ru.frogogo.whitelabel.ui.auth.code

sealed class AuthCodeCommand {
  object ClearError : AuthCodeCommand()
  object UserNotFoundError : AuthCodeCommand()
  object SomethingWentWrong : AuthCodeCommand()
  object CodeResendError : AuthCodeCommand()
  data class CodeValidationResult(val errorRes: Int?) : AuthCodeCommand()
}
