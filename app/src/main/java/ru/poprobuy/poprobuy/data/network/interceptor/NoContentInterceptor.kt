package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for changing response codes from 204 to 200 as Retrofit
 * with suspend invocation and  without return type can't handle such responses properly.
 */
class NoContentInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val response = chain.proceed(chain.request())

    return if (response.code != 204) {
      response
    } else {
      response.newBuilder().code(200).build()
    }
  }
}
