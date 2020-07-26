package ru.poprobuy.poprobuy.usecase.auth

sealed class AuthenticationResult {
  data class Success(val isNewUser: Boolean) : AuthenticationResult()
  object Error : AuthenticationResult()
  object NotFound : AuthenticationResult()
}
