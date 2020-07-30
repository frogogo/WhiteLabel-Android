package ru.poprobuy.poprobuy.data.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import ru.poprobuy.poprobuy.NetworkTest

class NoContentInterceptorTest : NetworkTest() {

  private val client = OkHttpClient.Builder()
    .addInterceptor(NoContentInterceptor())
    .build()

  @Test
  fun `interceptor should not change ordinary responses`() {
    mockWebServer.enqueue(MockResponse().apply { setResponseCode(201) })
    val call = client.newCall(createRequest()).execute()

    call.code shouldBeEqualTo 201
  }

  @Test
  fun `interceptor should not change error responses`() {
    mockWebServer.enqueue(MockResponse().apply { setResponseCode(404) })
    val call = client.newCall(createRequest()).execute()

    call.code shouldBeEqualTo 404
  }

  @Test
  fun `204 should be changed to 200`() {
    mockWebServer.enqueue(MockResponse().apply { setResponseCode(204) })
    val call = client.newCall(createRequest()).execute()

    call.code shouldBeEqualTo 200
  }
}
