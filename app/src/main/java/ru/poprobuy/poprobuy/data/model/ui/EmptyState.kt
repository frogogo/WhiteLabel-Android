package ru.poprobuy.poprobuy.data.model.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem

private const val ID = "ITEM_EMPTY_LIST"

open class EmptyState(
  @StringRes val titleResId: Int? = null,
  val title: String? = null,
  @DrawableRes val imageResId: Int
) : RecyclerViewItem {

  override fun getId(): Any = ID

}

object ReceiptsEmptyState : EmptyState(
  titleResId = R.string.receipts_empty_title,
  imageResId = R.drawable.ic_receipts_empty
)
