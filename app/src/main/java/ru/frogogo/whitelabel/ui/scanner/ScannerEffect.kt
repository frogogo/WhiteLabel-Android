package ru.frogogo.whitelabel.ui.scanner

import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

sealed class ScannerEffect {

  object ToggleFlash : ScannerEffect()

  data class ShowSuccess(
    val receipt: ReceiptUiModel,
  ) : ScannerEffect()

  data class ShowError(
    val error: String?,
  ) : ScannerEffect()
}
