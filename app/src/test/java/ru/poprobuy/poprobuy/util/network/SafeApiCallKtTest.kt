package ru.poprobuy.poprobuy.util.network

import com.squareup.moshi.JsonDataException
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class SafeApiCallKtTest {

  class MockError

  interface MockService {
    fun settings(): Response<ResponseBody>
  }

  private val mockService: MockService = mockk(relaxed = true)

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `test when retrofit call succeed returns success`() = runBlockingTest {
    val responseBody = "{}".toResponseBody()

    coEvery { mockService.settings() } returns Response.success(responseBody)

    val result = safeApiCall<ResponseBody, MockError> { mockService.settings() }

    assertTrue(result is NetworkResource.Success)
    assertEquals(result.data, responseBody)
  }

  @Test
  fun `test when retrofit call http fails returns proper error`() = runBlockingTest {
    val responseBody = "{}".toResponseBody()

    coEvery { mockService.settings() } returns Response.error(404, responseBody)

    val result = safeApiCall<ResponseBody, MockError> { mockService.settings() }

    assertTrue(result is NetworkResource.Error)
    assertTrue(result.error is NetworkError.HttpError)
    assertEquals(404, (result.error as NetworkError.HttpError).httpCode)
  }

  @Test
  fun `test when retrofit call IO fails returns proper error`() = runBlockingTest {
    coEvery { mockService.settings() } throws IOException("IO Exception")

    val result = safeApiCall<ResponseBody, MockError> { mockService.settings() }

    assertTrue(result is NetworkResource.Error)
    assertTrue(result.error is NetworkError.IOError)
  }

  @Test
  fun `test when retrofit call timeout fail returns proper error`() = runBlockingTest {
    coEvery { mockService.settings() } throws SocketTimeoutException()

    val result = safeApiCall<ResponseBody, MockError> { mockService.settings() }

    assertTrue(result is NetworkResource.Error)
    assertTrue(result.error is NetworkError.Timeout)
  }

  @Test
  fun `test when retrofit call fails for unknown reasons returns proper error`() = runBlockingTest {
    coEvery { mockService.settings() } throws Exception()

    val result = safeApiCall<ResponseBody, MockError> { mockService.settings() }

    assertTrue(result is NetworkResource.Error)
    assertTrue(result.error is NetworkError.Unknown)
  }

  @Test
  fun `test when json parsing fails returns proper error`() = runBlockingTest {
    coEvery { mockService.settings() } throws JsonDataException()

    val result = safeApiCall<ResponseBody, MockError> { mockService.settings() }

    assertTrue(result is NetworkResource.Error)
    assertTrue(result.error is NetworkError.JsonParsingError)
  }

}
