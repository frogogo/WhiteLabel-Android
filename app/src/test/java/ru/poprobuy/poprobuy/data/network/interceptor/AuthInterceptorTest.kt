package ru.poprobuy.poprobuy.data.network.interceptor

import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.BaseNetworkTest
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class AuthInterceptorTest : BaseNetworkTest() {

  private val userPreferences: UserPreferences = mockk(relaxed = true)

  private lateinit var interceptor: AuthInterceptor
  private lateinit var client: OkHttpClient

  @Before
  fun startUp() {
    interceptor = AuthInterceptor(userPreferences)
    client = OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .build()
  }

  @Test
  fun `interceptor should not add Authentication header if no token exists`() {
    every { userPreferences.accessToken } returns null
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(AUTHORIZATION_HEADER).shouldBeNull()
  }

  @Test
  fun `interceptor should add Authentication header`() {
    every { userPreferences.accessToken } returns TEST_TOKEN
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(AUTHORIZATION_HEADER) shouldBeEqualTo "Bearer $TEST_TOKEN"
  }

  companion object {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZ" +
        "SI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }

}
