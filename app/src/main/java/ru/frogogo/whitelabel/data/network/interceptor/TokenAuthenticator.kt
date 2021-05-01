package ru.frogogo.whitelabel.data.network.interceptor

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.extension.addAuthHeader
import ru.frogogo.whitelabel.usecase.auth.RefreshTokenUseCase
import java.net.HttpURLConnection

class TokenAuthenticator : Authenticator, KoinComponent {

  private val authRepository: AuthRepository by inject()
  private val refreshTokenUseCase: RefreshTokenUseCase by inject()
  private val logoutNotifier: AutoLogoutNotifier by inject()

  override fun authenticate(route: Route?, response: Response): Request? {
    val previousToken = authRepository.getAccessToken()

    if (isIgnoredRequest(response)) return null
    // Prevent authenticating requests with incomprehensible token
    if (!response.request.header(AUTHORIZATION_HEADER).equals("$BEARER_PREFIX $previousToken")) return null
    if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED) return null

    synchronized(this) {
      val currentToken = authRepository.getAccessToken()

      // Access token was refreshed in another thread
      if (previousToken != currentToken && currentToken != null) {
        return newAuthenticatedRequest(response, currentToken)
      }

      refreshToken()?.let { token ->
        return newAuthenticatedRequest(response, token)
      }
    }

    logoutUser()
    return null
  }

  /**
   * Returns true if response must be ignored
   */
  private fun isIgnoredRequest(response: Response): Boolean {
    val path = response.request.url.encodedPath
    return when {
      REQUEST_AUTH.matches(path) -> true
      else -> false
    }
  }

  private fun newAuthenticatedRequest(response: Response, token: String): Request =
    response.request
      .newBuilder()
      .addAuthHeader(token)
      .build()

  /**
   * @return new access token
   */
  private fun refreshToken(): String? = runBlocking(CoroutineName("TokenRefresh")) {
    repeat(REFRESH_ATTEMPTS) {
      val token = refreshTokenUseCase()
      if (token != null) {
        return@runBlocking token
      } else {
        delay(REFRESH_ATTEMPT_DELAY)
      }
    }
    return@runBlocking null
  }

  private fun logoutUser() {
    logoutNotifier.logout()
  }

  companion object {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val BEARER_PREFIX = "Bearer"

    private const val REFRESH_ATTEMPTS = 3
    private const val REFRESH_ATTEMPT_DELAY = 250L

    private val REQUEST_AUTH = Regex("/api/user_token")
  }
}
