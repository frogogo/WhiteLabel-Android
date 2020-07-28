package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import ru.poprobuy.poprobuy.BaseNetworkTest

class AcceptInterceptorTest : BaseNetworkTest() {

  private val client = OkHttpClient.Builder()
    .addInterceptor(AcceptInterceptor())
    .build()

  @Test
  fun `interceptor adds accept header`() {
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(HEADER_ACCEPT) shouldBeEqualTo JSON_CONTENT_TYPE
  }

  companion object {
    private const val HEADER_ACCEPT = "Accept"
    private const val JSON_CONTENT_TYPE = "application/json"
  }

}
