package ru.poprobuy.poprobuy.util.network

import retrofit2.Response
import ru.poprobuy.poprobuy.util.Result

/**
 * A generic class that contains data and status about loading this data.
 *
 * @param T success response data type.
 * @param E error response data type.
 */
sealed class NetworkResource<T, E>(
  open val data: T? = null,
  open val error: NetworkError<E>? = null,
) {
  class Success<T, E>(val response: Response<T>, override val data: T) : NetworkResource<T, E>()
  class Error<T, E>(val response: Response<T>?, override val error: NetworkError<E>) : NetworkResource<T, E>()
}

fun <T, E> NetworkResource<T, E>.mapToResult(): Result<T, NetworkError<E>> = mapToResult { it }

inline fun <T, R, E> NetworkResource<T, E>.mapToResult(
  successTransformation: (T) -> R,
): Result<R, NetworkError<E>> = when (this) {
  is NetworkResource.Success -> Result.Success(successTransformation.invoke(data))
  is NetworkResource.Error -> Result.Failure(error)
}
