package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiVersionInterceptor(
  private val apiVersion: Int,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val newRequest = request.newBuilder().apply {
      // Add api version only if request doesn't have it
      if (!request.hasApiVersionHeader()) {
        header(HEADER_API_VERSION, apiVersion.toString())
      }
    }.build()

    return chain.proceed(newRequest)
  }

  /**
   * @return true whether request contains [HEADER_API_VERSION] header
   */
  private fun Request.hasApiVersionHeader(): Boolean = headers.names().contains(HEADER_API_VERSION)

  companion object {
    private const val HEADER_API_VERSION = "API-Version"
  }

}
