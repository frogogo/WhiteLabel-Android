package ru.frogogo.whitelabel.ui.auth.phone

sealed class AuthPhoneCommand {
  object ClearError : AuthPhoneCommand()
  object SomethingWentWrong : AuthPhoneCommand()
  object TooManyRequestsError : AuthPhoneCommand()
  object ShowLogoutDialog : AuthPhoneCommand()
  data class PhoneValidationResult(val errorRes: Int?) : AuthPhoneCommand()
}
