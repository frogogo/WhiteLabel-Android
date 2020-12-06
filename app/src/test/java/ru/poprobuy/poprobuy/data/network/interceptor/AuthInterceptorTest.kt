package ru.poprobuy.poprobuy.data.network.interceptor

import io.mockk.*
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.NetworkTest
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class AuthInterceptorTest : NetworkTest() {

  private val userPreferences: UserPreferences = mockk(relaxed = true)

  private lateinit var interceptor: AuthInterceptor
  private lateinit var client: OkHttpClient

  @BeforeEach
  fun startUp() {
    interceptor = AuthInterceptor(userPreferences)
    client = OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .build()
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `interceptor should not add Authentication header if no token exists`() {
    every { userPreferences.accessToken } returns null
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(AUTHORIZATION_HEADER).shouldBeNull()
    verifySequence {
      userPreferences.accessToken
    }
    confirmVerified()
  }

  @Test
  fun `interceptor should add Authentication header`() {
    every { userPreferences.accessToken } returns TEST_TOKEN
    mockWebServer.enqueue(MockResponse())

    client.newCall(createRequest()).execute()
    val recorded = mockWebServer.takeRequest()

    recorded.getHeader(AUTHORIZATION_HEADER) shouldBeEqualTo "Bearer $TEST_TOKEN"
    verifySequence {
      userPreferences.accessToken
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(userPreferences)
  }

  companion object {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZ" +
        "SI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }

}
