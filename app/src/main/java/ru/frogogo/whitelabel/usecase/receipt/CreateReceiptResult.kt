package ru.frogogo.whitelabel.usecase.receipt

import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

sealed class CreateReceiptResult {
  object Error : CreateReceiptResult()
  data class Success(val receipt: ReceiptUiModel) : CreateReceiptResult()
  data class ValidationError(val error: String?) : CreateReceiptResult()
}
