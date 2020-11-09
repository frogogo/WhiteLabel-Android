package ru.poprobuy.poprobuy.data.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

@Parcelize
data class ReceiptUiModel(
  val id: Int,
  val number: Int,
  val shopName: String?,
  val date: Date,
  val value: Int,
  val state: ReceiptState
) : RecyclerViewItem, Parcelable {

  override fun getId(): Any = "$ID$id"

  companion object {
    private const val ID = "ITEM_RECEIPT"
  }

}
