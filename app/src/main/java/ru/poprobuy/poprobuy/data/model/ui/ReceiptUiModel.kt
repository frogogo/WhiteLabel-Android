package ru.poprobuy.poprobuy.data.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import java.util.*

private const val ID = "ITEM_RECEIPT"

@Parcelize
data class ReceiptUiModel(
  val id: Int,
  val shopName: String?,
  val date: Date,
  val value: Int,
  val status: ReceiptStatus
) : RecyclerViewItem, Parcelable {

  override fun getId(): Any = "$ID$id"

}
