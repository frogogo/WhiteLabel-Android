package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationRequest
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequest
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequestResponse
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

  fun saveAuthToken(token: String) {
    userPreferences.accessToken = token
  }

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
