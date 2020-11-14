package ru.poprobuy.poprobuy.util.network

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import retrofit2.Response
import ru.poprobuy.poprobuy.util.Result

class NetworkResourceTest {

  @Test
  fun `mapToResult returns value`() {
    val resource = NetworkResource.Success<Int, String>(Response.success(1), 1)
    val result = resource.mapToResult()
    result shouldBeEqualTo Result.Success(1)
  }

  @Test
  fun `mapToResult with transformation returns transformed value`() {
    val resource = NetworkResource.Success<Int, String>(Response.success(1), 1)
    val result = resource.mapToResult { it.toString() }
    result shouldBeEqualTo Result.Success("1")
  }

  @Test
  fun `mapToResult returns failure`() {
    val error = NetworkError.Timeout<String>()
    val resource = NetworkResource.Error<Int, String>(null, error)
    val result = resource.mapToResult()
    result shouldBeEqualTo Result.Failure(error)
  }

  @Test
  fun `mapToResult with transformation returns failure`() {
    val error = NetworkError.Timeout<String>()
    val resource = NetworkResource.Error<Int, String>(null, error)
    val result = resource.mapToResult { it.toString() }
    result shouldBeEqualTo Result.Failure(error)
  }

}
