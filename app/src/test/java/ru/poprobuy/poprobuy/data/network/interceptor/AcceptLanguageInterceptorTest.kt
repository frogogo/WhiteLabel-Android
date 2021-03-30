package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.NetworkTest

class AcceptLanguageInterceptorTest : NetworkTest() {

  private val client = OkHttpClient.Builder()
    .addInterceptor(AcceptLanguageInterceptor(ACCEPT_LANGUAGE))
    .build()

  @Test
  fun `interceptor should add accept language header`() {
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(HEADER_ACCEPT_LANGUAGE) shouldBeEqualTo ACCEPT_LANGUAGE
  }

  companion object {
    private const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    private const val ACCEPT_LANGUAGE = "en"
  }
}
