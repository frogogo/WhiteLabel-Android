package ru.poprobuy.poprobuy.data.model.ui.home

import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel

sealed class HomeState : RecyclerViewItem {

  object Empty : HomeState() {
    override fun getId(): Any = ID_EMPTY
  }

  data class Receipt(
    val receipt: ReceiptUiModel
  ) : HomeState() {
    override fun getId(): Any = ID_RECEIPT
  }

  companion object {
    private const val ID_EMPTY = "ITEM_HOME_EMPTY"
    private const val ID_RECEIPT = "ITEM_HOME_RECEIPT"
  }

}
