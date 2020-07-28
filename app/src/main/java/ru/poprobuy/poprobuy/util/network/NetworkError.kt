package ru.poprobuy.poprobuy.util.network

import com.squareup.moshi.JsonDataException

/**
 * A generic network error class.
 *
 * @param E error response data type.
 */
sealed class NetworkError<E> {
  class HttpError<E>(val httpCode: Int, val data: E?) : NetworkError<E>()
  class IOError<E> : NetworkError<E>()
  class Timeout<E> : NetworkError<E>()
  class Unknown<E> : NetworkError<E>()
  class JsonParsingError<E>(val exception: JsonDataException) : NetworkError<E>()

  fun isHttpErrorWithCode(code: Int): Boolean = this is HttpError && this.httpCode == code

}
