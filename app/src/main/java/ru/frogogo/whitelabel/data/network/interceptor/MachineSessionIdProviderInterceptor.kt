package ru.frogogo.whitelabel.data.network.interceptor

import com.github.ajalt.timberkt.d
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.frogogo.whitelabel.data.SessionStorage
import ru.frogogo.whitelabel.data.network.annotation.ProvideMachineSessionId
import ru.frogogo.whitelabel.extension.getAnnotation
import ru.frogogo.whitelabel.util.NetworkConstants

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
