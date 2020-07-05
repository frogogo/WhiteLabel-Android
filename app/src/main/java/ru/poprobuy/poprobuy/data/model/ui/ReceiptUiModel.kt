package ru.poprobuy.poprobuy.data.model.ui

import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import java.util.*

private const val ID = "ITEM_RECEIPT"

data class ReceiptUiModel(
  val id: Int,
  val shopName: String?,
  val date: Date,
  val value: Int,
  val status: ReceiptStatus
) : RecyclerViewItem {

  override fun getId(): Any = "$ID$id"

}
