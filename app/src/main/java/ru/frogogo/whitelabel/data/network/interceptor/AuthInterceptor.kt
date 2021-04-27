package ru.frogogo.whitelabel.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.frogogo.whitelabel.data.preferences.UserPreferences
import ru.frogogo.whitelabel.extension.addAuthHeader

internal class AuthInterceptor(
  private val userPrefs: UserPreferences,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val accessToken = userPrefs.accessToken
    if (accessToken.isNullOrEmpty()) {
      return chain.proceed(chain.request())
    }

    val request = chain.request()
      .newBuilder()
      .addAuthHeader(accessToken)
      .build()

    return chain.proceed(request)
  }
}
