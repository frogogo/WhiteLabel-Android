package ru.poprobuy.poprobuy.data.network.interceptor

import io.mockk.clearAllMocks
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.test.base.NetworkTest
import ru.poprobuy.poprobuy.data.SessionStorage
import ru.poprobuy.poprobuy.data.network.annotation.TakeMachineSessionId
import ru.poprobuy.poprobuy.util.NetworkConstants

internal class MachineSessionIdTakeInterceptorTest : NetworkTest() {

  private lateinit var interceptor: MachineSessionIdTakeInterceptor
  private lateinit var client: OkHttpClient
  private lateinit var apiService: ApiService

  private val sessionStorage: SessionStorage = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    interceptor = MachineSessionIdTakeInterceptor(sessionStorage)
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
    val response = MockResponse()
      .addHeader(NetworkConstants.HEADER_SESSION_ID, DataFixtures.SESSION_ID)
    mockWebServer.enqueue(response)

    apiService.callWithTakeSessionId().execute()

    coVerifySequence {
      sessionStorage.saveSessionId(DataFixtures.SESSION_ID)
    }
    confirmVerified()
  }

  @Test
  fun `interceptor shouldn't throw exception when annotated call's response doesn't have session`() {
    mockWebServer.enqueue(MockResponse())

    assertDoesNotThrow {
      apiService.callWithTakeSessionId().execute()
    }
    confirmVerified()
  }

  @Test
  fun `interceptor shouldn't take sessionId from not annotated call`() {
    val response = MockResponse()
      .addHeader(NetworkConstants.HEADER_SESSION_ID, DataFixtures.SESSION_ID)
    mockWebServer.enqueue(response)

    apiService.callWithoutTakeSessionId().execute()

    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(sessionStorage)
  }

  private interface ApiService {

    @TakeMachineSessionId
    @GET("/")
    fun callWithTakeSessionId(): Call<Unit>

    @GET("/")
    fun callWithoutTakeSessionId(): Call<Unit>

  }

}
