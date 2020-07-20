package ru.poprobuy.poprobuy.usecase.auth

import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse

sealed class AuthenticationResult {
  class Success(val response: AuthenticationResponse) : AuthenticationResult()
  object Error : AuthenticationResult()
  object NotFound : AuthenticationResult()
}
