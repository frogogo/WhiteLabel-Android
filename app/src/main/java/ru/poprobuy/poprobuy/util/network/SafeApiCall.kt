package ru.poprobuy.poprobuy.util.network

import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.squareup.moshi.JsonDataException
import okhttp3.ResponseBody
import retrofit2.Response
import ru.poprobuy.poprobuy.extension.fromJson
import ru.poprobuy.poprobuy.util.moshi.MoshiUtils
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * This method wrap a Retrofit [Response] and handles properly all types of errors.
 * Without this wrapper you should surround with a try/catch every api call to avoid
 * unexpected crashes (no internet connection, timeout).
 *
 * @param block a suspend Retrofit call that returns a [Response]
 * @return a [NetworkResource] response.
 */
inline fun <T, reified E : Any> safeApiCall(block: () -> Response<T>): NetworkResource<T, E> {
  val result = runCatching(block)
  return if (result.isSuccess) {
    val response = result.getOrNull()!!
    if (response.isSuccessful) {
      val responseBody = response.body()!!
      NetworkResource.Success<T, E>(response, responseBody)
    } else {
      val errorBody = response.errorBody()
      when (errorBody != null) {
        true -> NetworkResource.Error(response, NetworkError.HttpError(response.code(), deserializeError(errorBody)))
        false -> NetworkResource.Error<T, E>(response, NetworkError.HttpError(response.code(), null))
      }
    }
  } else {
    val exception = result.exceptionOrNull()
    d(exception) { "Network exception occurred" }
    return handleException(exception)
  }
}

/**
 * Handles exception and returns [NetworkResource] with proper [NetworkError]
 */
fun <T, E : Any> handleException(exception: Throwable?): NetworkResource<T, E> = when (exception) {
  is SocketTimeoutException -> {
    NetworkResource.Error(null, NetworkError.Timeout())
  }
  is IOException -> {
    NetworkResource.Error(null, NetworkError.IOError())
  }
  is JsonDataException -> {
    e(exception) { "Error while parsing json" }
    NetworkResource.Error(null, NetworkError.JsonParsingError(exception))
  }
  else -> {
    NetworkResource.Error(null, NetworkError.Unknown())
  }
}

/**
 * Deserialize an error response body using the specified [E] type.
 */
inline fun <reified E : Any> deserializeError(responseBody: ResponseBody): E? {
  val str = responseBody.string()
  return runCatching {
    MoshiUtils.networkErrorParserMoshi.fromJson<E>(str)
  }.onFailure { e { it.toString() } }.getOrNull()
}
