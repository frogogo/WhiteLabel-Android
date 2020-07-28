package ru.poprobuy.poprobuy.ui.auth.phone

sealed class AuthPhoneCommand {
  object ClearError : AuthPhoneCommand()
  object SomethingWentWrong : AuthPhoneCommand()
  object TooManyRequestsError : AuthPhoneCommand()
  data class PhoneValidationResult(val errorRes: Int?) : AuthPhoneCommand()
}
