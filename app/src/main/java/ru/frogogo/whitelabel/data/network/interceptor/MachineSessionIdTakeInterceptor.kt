package ru.frogogo.whitelabel.data.network.interceptor

import com.github.ajalt.timberkt.d
import okhttp3.Interceptor
import okhttp3.Response
import ru.frogogo.whitelabel.data.SessionStorage
import ru.frogogo.whitelabel.data.network.annotation.TakeMachineSessionId
import ru.frogogo.whitelabel.extension.getAnnotation
import ru.frogogo.whitelabel.util.NetworkConstants

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
