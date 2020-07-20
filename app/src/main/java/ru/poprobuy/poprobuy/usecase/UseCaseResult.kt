package ru.poprobuy.poprobuy.usecase

sealed class UseCaseResult<T, E> {
  class Success<T, E>(val data: T) : UseCaseResult<T, E>()
  class Failure<T, E> : UseCaseResult<T, E>()
}
