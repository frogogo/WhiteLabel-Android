package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.NetworkTest

class UserAgentInterceptorTest : NetworkTest() {

  private val client = OkHttpClient.Builder()
    .addInterceptor(UserAgentInterceptor(USER_AGENT))
    .build()

  @Test
  fun `interceptor adds user agent`() {
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(HEADER_USER_AGENT) shouldBeEqualTo USER_AGENT
  }

  companion object {
    private const val HEADER_USER_AGENT = "User-Agent"
    private const val USER_AGENT = "Poprobuy/0.0.1 (ru.poprobuy.poprobuy; build:1; Android 10) Retrofit/2.9.0"
  }
}
