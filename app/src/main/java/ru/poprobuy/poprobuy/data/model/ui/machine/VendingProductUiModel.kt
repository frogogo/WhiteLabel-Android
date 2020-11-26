package ru.poprobuy.poprobuy.data.model.ui.machine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.poprobuy.poprobuy.dictionary.VendingProductState

@Parcelize
data class VendingProductUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val state: VendingProductState,
) : Parcelable {

  fun isActive(): Boolean = state == VendingProductState.AVAILABLE

}
