package ru.frogogo.whitelabel.data.model.ui.receipt

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.dictionary.ReceiptState
import java.util.*

@Keep
@Parcelize
data class ReceiptUiModel(
  val id: Int,
  val number: Int,
  val date: Date,
  val value: Int,
  val state: ReceiptState,
  val rejectReason: ReceiptRejectReasonUiModel?,
) : RecyclerViewItem, Parcelable {

  override fun getId(): Any = "$ID$id"

  companion object {
    private const val ID = "ITEM_RECEIPT"
  }
}
