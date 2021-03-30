package ru.poprobuy.poprobuy.data.network.interceptor

import com.github.ajalt.timberkt.d
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.poprobuy.poprobuy.data.SessionStorage
import ru.poprobuy.poprobuy.data.network.annotation.ProvideMachineSessionId
import ru.poprobuy.poprobuy.extension.getAnnotation
import ru.poprobuy.poprobuy.util.NetworkConstants

class MachineSessionIdProviderInterceptor(
  private val sessionStorage: SessionStorage,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.getAnnotation(ProvideMachineSessionId::class)?.run {
      provideSessionId(chain.request())
    } ?: chain.request()

    return chain.proceed(request)
  }

  private fun provideSessionId(request: Request): Request {
    val builder = request.newBuilder()

    sessionStorage.getSessionId()?.let { sessionId ->
      d { "Authorizing request with sessionId - $sessionId" }
      builder.addHeader(NetworkConstants.HEADER_SESSION_ID, sessionId)
    }

    return builder.build()
  }
}
