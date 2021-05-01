package ru.frogogo.whitelabel.data.network.interceptor

import io.mockk.*
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import ru.frogogo.test.DataFixtures
import ru.frogogo.test.base.NetworkTest
import ru.frogogo.whitelabel.data.SessionStorage
import ru.frogogo.whitelabel.data.network.annotation.ProvideMachineSessionId
import ru.frogogo.whitelabel.util.NetworkConstants

internal class MachineSessionIdProviderInterceptorTest : NetworkTest() {

  private lateinit var interceptor: MachineSessionIdProviderInterceptor
  private lateinit var client: OkHttpClient
  private lateinit var apiService: ApiService

  private val sessionStorage: SessionStorage = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    interceptor = MachineSessionIdProviderInterceptor(sessionStorage)
    client = OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .build()

    apiService = Retrofit.Builder()
      .baseUrl(mockWebServer.url("/"))
      .client(client)
      .build()
      .create(ApiService::class.java)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `interceptor should take sessionId from annotated call`() {
    every { sessionStorage.getSessionId() } returns DataFixtures.SESSION_ID
    mockWebServer.enqueue(MockResponse())

    apiService.callWithProvideSessionId().execute()

    val request = mockWebServer.takeRequest()
    request.getHeader(NetworkConstants.HEADER_SESSION_ID) shouldBeEqualTo DataFixtures.SESSION_ID

    coVerifySequence {
      sessionStorage.getSessionId()
    }
    confirmVerified()
  }

  @Test
  fun `interceptor shouldn't provide sessionId when sessionStorage doesn't contain it`() {
    every { sessionStorage.getSessionId() } returns null
    mockWebServer.enqueue(MockResponse())

    apiService.callWithProvideSessionId().execute()

    val request = mockWebServer.takeRequest()
    request.getHeader(NetworkConstants.HEADER_SESSION_ID).shouldBeNull()

    coVerifySequence {
      sessionStorage.getSessionId()
    }
    confirmVerified()
  }

  @Test
  fun `interceptor shouldn't provide sessionId to not annotated call`() {
    every { sessionStorage.getSessionId() } returns DataFixtures.SESSION_ID
    mockWebServer.enqueue(MockResponse())

    apiService.callWithoutProvideSessionId().execute()

    val request = mockWebServer.takeRequest()
    request.getHeader(NetworkConstants.HEADER_SESSION_ID).shouldBeNull()
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(sessionStorage)
  }

  private interface ApiService {

    @ProvideMachineSessionId
    @GET("/")
    fun callWithProvideSessionId(): Call<Unit>

    @GET("/")
    fun callWithoutProvideSessionId(): Call<Unit>
  }
}
