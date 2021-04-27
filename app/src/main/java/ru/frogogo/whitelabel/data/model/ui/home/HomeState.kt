package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

sealed class HomeState : RecyclerViewItem {

  object Empty : HomeState() {
    override fun getId(): Any = ID_EMPTY
  }

  data class Receipt(
    val receipt: ReceiptUiModel,
  ) : HomeState() {
    override fun getId(): Any = ID_RECEIPT
  }

  companion object {
    private const val ID_EMPTY = "ITEM_HOME_EMPTY"
    private const val ID_RECEIPT = "ITEM_HOME_RECEIPT"
  }
}
