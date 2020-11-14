package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AcceptLanguageInterceptor(
  private val acceptLanguage: String = RU,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val newRequest = chain.request().newBuilder().apply {
      // Add accept language header
      header(HEADER_ACCEPT_LANGUAGE, acceptLanguage)
    }.build()

    return chain.proceed(newRequest)
  }

  companion object {
    private const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    private const val RU = "ru"
  }

}
