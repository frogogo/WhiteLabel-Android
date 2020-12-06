package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.auth.*
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class AuthRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: PoprobuyApi,
  private val userPreferences: UserPreferences,
) : Repository(dispatcher), AuthRepository {

  override suspend fun requestConfirmationCode(
    phoneNumber: String,
  ): Result<ConfirmationCodeRequestResponse, NetworkError<ErrorResponse>> = withIOContext {
    val request = ConfirmationCodeRequest(phoneNumber)
    apiCall { api.requestPasswordCode(request) }.mapToResult()
  }

  override suspend fun authenticate(
    phoneNumber: String,
    password: String,
  ): Result<AuthenticationResponse, NetworkError<ErrorResponse>> = withIOContext {
    val request = AuthenticationRequest(phoneNumber = phoneNumber, password = password)
    apiCall { api.authenticate(request) }.mapToResult()
  }

  override suspend fun refreshToken(
    refreshToken: String,
  ): Result<AuthenticationResponse, NetworkError<ErrorResponse>> = withIOContext {
    val request = TokenRefreshRequest(refreshToken)
    apiCall { api.refreshToken(request) }.mapToResult()
  }

  override fun saveAuthTokens(accessToken: String, refreshToken: String) {
    userPreferences.apply {
      this.accessToken = accessToken
      this.refreshToken = refreshToken
    }
  }

  override fun getAccessToken(): String? = userPreferences.accessToken

  override fun getRefreshToken(): String? = userPreferences.refreshToken

  override fun setPolicyAccepted() {
    userPreferences.policyAccepted = true
  }

  override fun setUserAuthorized() {
    userPreferences.isLoggedIn = true
  }

  override fun logout() {
    userPreferences.clearData()
  }

}
