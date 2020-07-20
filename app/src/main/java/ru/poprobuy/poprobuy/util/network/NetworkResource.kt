package ru.poprobuy.poprobuy.util.network

import retrofit2.Response

/**
 * A generic class that contains data and status about loading this data.
 *
 * @param T success response data type.
 * @param E error response data type.
 */
sealed class NetworkResource<T, E>(
  open val data: T? = null,
  open val error: NetworkError<E>? = null
) {
  class Success<T, E>(val response: Response<T>, override val data: T) : NetworkResource<T, E>()
  class Error<T, E>(val response: Response<T>?, override val error: NetworkError<E>) : NetworkResource<T, E>()
}
