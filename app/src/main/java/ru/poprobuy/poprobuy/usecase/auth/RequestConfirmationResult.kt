package ru.poprobuy.poprobuy.usecase.auth

sealed class RequestConfirmationResult {
  data class Success(val refreshRate: Int) : RequestConfirmationResult()
  object Error : RequestConfirmationResult()
  object TooManyRequests : RequestConfirmationResult()
}
