package ru.poprobuy.poprobuy.data.network.interceptor

import com.github.ajalt.timberkt.d
import okhttp3.Interceptor
import okhttp3.Response
import ru.poprobuy.poprobuy.data.SessionStorage
import ru.poprobuy.poprobuy.data.network.annotation.TakeMachineSessionId
import ru.poprobuy.poprobuy.extension.getAnnotation
import ru.poprobuy.poprobuy.util.NetworkConstants

class MachineSessionIdTakeInterceptor(
  private val sessionStorage: SessionStorage,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val response = chain.proceed(chain.request())

    chain.getAnnotation(TakeMachineSessionId::class)?.let {
      saveSessionId(response)
    }

    return response
  }

  private fun saveSessionId(response: Response) {
    response.header(NetworkConstants.HEADER_SESSION_ID)?.let { sessionId ->
      d { "Taking sessionId from response - $sessionId" }
      sessionStorage.saveSessionId(sessionId)
    }
  }

}
