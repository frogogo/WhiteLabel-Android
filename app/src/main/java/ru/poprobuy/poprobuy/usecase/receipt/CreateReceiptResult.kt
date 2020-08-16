package ru.poprobuy.poprobuy.usecase.receipt

sealed class CreateReceiptResult {
  object Success : CreateReceiptResult()
  object Error : CreateReceiptResult()
  data class ValidationError(val errorRes: Int) : CreateReceiptResult()
}
