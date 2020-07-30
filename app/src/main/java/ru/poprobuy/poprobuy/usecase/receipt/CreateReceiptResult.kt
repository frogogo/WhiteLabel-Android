package ru.poprobuy.poprobuy.usecase.receipt

sealed class CreateReceiptResult {
  object Success : CreateReceiptResult()
  object Error : CreateReceiptResult()

  // TODO: 29.07.2020 Pass error
  object UnprocessableEntity : CreateReceiptResult()
}
