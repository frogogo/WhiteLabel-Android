package ru.frogogo.whitelabel.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

private const val HTTP_200 = 200
private const val HTTP_204 = 204

/**
 * Interceptor for changing response codes from 204 to 200 as Retrofit
 * with suspend invocation and  without return type can't handle such responses properly.
 */
class NoContentInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val response = chain.proceed(chain.request())

    return if (response.code == HTTP_204) {
      response.newBuilder().code(HTTP_200).build()
    } else {
      response
    }
  }
}
