package ru.poprobuy.poprobuy.data.model.ui.receipt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

@Parcelize
data class ReceiptUiModel(
  val id: Int,
  val number: Int,
  val date: Date,
  val value: Int,
  val state: ReceiptState,
  val distributionNetwork: ReceiptDistributionNetworkUiModel?,
  val product: ReceiptProductUiModel?,
  val rejectReason: ReceiptRejectReasonUiModel?,
) : RecyclerViewItem, Parcelable {

  override fun getId(): Any = "$ID$id"

  companion object {
    private const val ID = "ITEM_RECEIPT"
  }

}
