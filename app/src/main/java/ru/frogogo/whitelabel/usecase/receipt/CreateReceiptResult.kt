package ru.frogogo.whitelabel.usecase.receipt

sealed class CreateReceiptResult {
  object Success : CreateReceiptResult()
  object Error : CreateReceiptResult()
  data class ValidationError(val error: String?) : CreateReceiptResult()
}
