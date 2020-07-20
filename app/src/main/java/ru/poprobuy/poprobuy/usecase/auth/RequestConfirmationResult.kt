package ru.poprobuy.poprobuy.usecase.auth

sealed class RequestConfirmationResult {
  object Success : RequestConfirmationResult()
  object Error : RequestConfirmationResult()
  object TooManyRequests : RequestConfirmationResult()
}
