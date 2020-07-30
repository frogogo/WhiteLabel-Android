package ru.poprobuy.poprobuy.usecase

sealed class UseCaseResult<T, E> {
  class Success<T, E>(val data: T) : UseCaseResult<T, E>()
  class Failure<T, E>(val error: E) : UseCaseResult<T, E>()
}

fun <T, E> UseCaseResult<T, E>.onSuccess(onSuccess: (T) -> Unit): UseCaseResult<T, E> {
  if (this is UseCaseResult.Success) onSuccess(data)
  return this
}

fun <T, E> UseCaseResult<T, E>.onFailure(onFailure: () -> Unit): UseCaseResult<T, E> {
  if (this is UseCaseResult.Failure) onFailure()
  return this
}
