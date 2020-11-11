package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(
  private val userAgent: String,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val newRequest = chain.request().newBuilder().apply {
      // Add user agent header
      header(HEADER_USER_AGENT, userAgent)
    }.build()

    return chain.proceed(newRequest)
  }

  companion object {
    private const val HEADER_USER_AGENT = "User-Agent"
  }

}
