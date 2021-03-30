package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AcceptInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val newRequest = chain.request().newBuilder().apply {
      // Add accept header
      header(HEADER_ACCEPT, JSON_CONTENT_TYPE)
    }.build()

    return chain.proceed(newRequest)
  }

  companion object {
    private const val HEADER_ACCEPT = "Accept"
    private const val JSON_CONTENT_TYPE = "application/json"
  }
}
