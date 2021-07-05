package ru.frogogo.whitelabel.ui.home

import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

sealed class HomeEffect {

  object ShowLoadingError : HomeEffect()

  data class OpenReceiptInfoDialog(
    val receipt: ReceiptUiModel,
  ) : HomeEffect()
}
