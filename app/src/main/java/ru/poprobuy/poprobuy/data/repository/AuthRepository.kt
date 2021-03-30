package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.poprobuy.poprobuy.util.network.NetworkError

interface AuthRepository {

  suspend fun requestConfirmationCode(
    phoneNumber: String,
  ): Result<ConfirmationCodeRequestResponse, NetworkError<ErrorResponse>>

  suspend fun authenticate(
    phoneNumber: String,
    password: String,
  ): Result<AuthenticationResponse, NetworkError<ErrorResponse>>

  suspend fun refreshToken(
    refreshToken: String,
  ): Result<AuthenticationResponse, NetworkError<ErrorResponse>>

  fun saveAuthTokens(accessToken: String, refreshToken: String)

  fun getAccessToken(): String?

  fun getRefreshToken(): String?

  fun setPolicyAccepted()

  fun setUserAuthorized()

  fun logout()
}
