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

  companion object

}

inline fun <E> NetworkError<E>.onHttpErrorWithCode(code: Int, callback: (NetworkError.HttpError<E>) -> Unit) {
  if (this is NetworkError.HttpError<E> && this.httpCode == code) callback(this)
}
