package ru.poprobuy.poprobuy.data.network.interceptor

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.poprobuy.poprobuy.BuildConfig

/**
 * Interceptor for adding poprobuy-specific headers to all requests
 */
class PoprobuyInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val newRequest = request.newBuilder().apply {
      // Add accept header
      header(HEADER_ACCEPT, JSON_CONTENT_TYPE)

      // Add user agent header
      header(HEADER_USER_AGENT, getUserAgent())

      // Add api version only if request doesn't have it
      if (!request.hasApiVersionHeader()) {
        header(HEADER_API_VERSION, BuildConfig.API_VERSION.toString())
      }
    }.build()

    return chain.proceed(newRequest)
  }

  // Poprobuy/1.0.0 (ru.poprobuy.poprobuy; build:43; Android 9) Retrofit/1.0
  private fun getUserAgent(): String {
    val versionCode = BuildConfig.VERSION_CODE
    val versionName = BuildConfig.VERSION_NAME
    val androidVersion = Build.VERSION.RELEASE
    val retrofitVersion = BuildConfig.RETROFIT_VERSION

    return buildString {
      // App data
      append("Poprobuy/$versionName ")

      // App data 2
      append("($PACKAGE_NAME; build:$versionCode; Android $androidVersion) ")

      // Retrofit data
      append("Retrofit/$retrofitVersion")
    }
  }

  /**
   * @return true whether request contains [HEADER_API_VERSION] header
   */
  private fun Request.hasApiVersionHeader(): Boolean = headers.names().contains(HEADER_API_VERSION)

  companion object {
    private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
    private const val JSON_CONTENT_TYPE = "application/json"

    private const val HEADER_USER_AGENT = "User-Agent"
    private const val HEADER_API_VERSION = "API-Version"
    private const val HEADER_ACCEPT = "Accept"
  }

}
