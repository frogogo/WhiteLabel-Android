package ru.frogogo.whitelabel.data.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.frogogo.test.base.NetworkTest

class ApiVersionInterceptorTest : NetworkTest() {

  private val client = OkHttpClient.Builder()
    .addInterceptor(ApiVersionInterceptor(API_VERSION))
    .build()

  @Test
  fun `interceptor adds version header to requests`() {
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(HEADER_API_VERSION) shouldBeEqualTo API_VERSION.toString()
  }

  @Test
  fun `interceptor don't override version header in requests`() {
    val version = 2
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest().modify {
      addHeader(HEADER_API_VERSION, version.toString())
    }).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(HEADER_API_VERSION) shouldBeEqualTo version.toString()
  }

  companion object {
    private const val API_VERSION = 1
    private const val HEADER_API_VERSION = "API-Version"
  }
}
