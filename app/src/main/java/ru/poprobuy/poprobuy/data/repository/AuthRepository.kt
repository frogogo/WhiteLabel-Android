package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.auth.*
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class AuthRepository(
  private val api: PoprobuyApi,
  private val userPreferences: UserPreferences,
) {

  suspend fun requestConfirmationCode(
    phoneNumber: String,
  ): Result<ConfirmationCodeRequestResponse, NetworkError<ErrorResponse>> {
    val request = ConfirmationCodeRequest(phoneNumber)
    return apiCall { api.requestPasswordCode(request) }.mapToResult()
  }

  suspend fun authenticate(
    phoneNumber: String,
    password: String,
  ): Result<AuthenticationResponse, NetworkError<ErrorResponse>> {
    val request = AuthenticationRequest(phoneNumber = phoneNumber, password = password)
    return apiCall { api.authenticate(request) }.mapToResult()
  }

  suspend fun refreshToken(refreshToken: String): Result<AuthenticationResponse, NetworkError<ErrorResponse>> {
    val request = TokenRefreshRequest(refreshToken)
    return apiCall { api.refreshToken(request) }.mapToResult()
  }

  fun saveAuthTokens(accessToken: String, refreshToken: String) {
    userPreferences.apply {
      this.accessToken = accessToken
      this.refreshToken = refreshToken
    }
  }

  fun getAccessToken(): String? = userPreferences.accessToken

  fun getRefreshToken(): String? = userPreferences.refreshToken

  fun setPolicyAccepted() {
    userPreferences.policyAccepted = true
  }

  fun setUserAuthorized() {
    userPreferences.isLoggedIn = true
  }

  fun logout() {
    userPreferences.clearData()
  }

}
