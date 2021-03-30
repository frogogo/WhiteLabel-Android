package ru.poprobuy.poprobuy.data.model.ui.machine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.poprobuy.poprobuy.core.recycler.RecyclerViewItem

@Parcelize
data class VendingCellUiModel(
  val id: Int,
  val product: VendingProductUiModel,
) : RecyclerViewItem, Parcelable {

  override fun getId(): Any = "ITEM_CELL_$id"
}
