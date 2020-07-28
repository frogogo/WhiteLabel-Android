package ru.poprobuy.poprobuy

import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer

open class BaseNetworkTest {

  val mockWebServer = MockWebServer().apply { start() }

  fun createRequest(): Request = Request.Builder().url(mockWebServer.url("/")).build()

  fun Request.modify(action: Request.Builder.() -> Unit): Request = newBuilder().apply(action).build()

}
