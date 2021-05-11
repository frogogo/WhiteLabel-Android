package ru.frogogo.whitelabel.data.model.ui.coupon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.PhotoUiModel

@Parcelize
data class CouponUiModel(
  val id: Int,
  val name: String,
  val steps: List<String>,
  val photo: PhotoUiModel,
  val code: CouponCodeUiModel,
) : RecyclerViewItem, Parcelable {

  override fun getId(): Any = "ITEM_COUPON$id"
}
