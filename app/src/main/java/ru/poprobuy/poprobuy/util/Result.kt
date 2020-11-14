package ru.poprobuy.poprobuy.util

sealed class Result<T, E> {
  data class Success<T, E>(val value: T) : Result<T, E>()
  data class Failure<T, E>(val error: E) : Result<T, E>()
}

fun <T, E> Result<T, E>.successOrNull(): T? = (this as? Result.Success)?.value

fun <T, E> Result<T, E>.isSuccess(): Boolean = this is Result.Success

inline fun <T, E> Result<T, E>.handle(
  onFailure: (E) -> Unit = {},
  onSuccess: (T) -> Unit = {},
) {
  when (this) {
    is Result.Success -> onSuccess.invoke(value)
    is Result.Failure -> onFailure.invoke(error)
  }
}

inline fun <T, E> Result<T, E>.doOnSuccess(block: (T) -> Unit): Result<T, E> {
  handle(onSuccess = block)
  return this
}

inline fun <T, E> Result<T, E>.doOnFailure(block: (E) -> Unit): Result<T, E> {
  handle(onFailure = block)
  return this
}
