package ru.frogogo.whitelabel.ui.profile.receipts

import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

sealed class ReceiptsEffect {

  object ShowError : ReceiptsEffect()

  data class OpenReceiptInfoDialog(
    val receipt: ReceiptUiModel,
  ) : ReceiptsEffect()
}
