package ru.frogogo.whitelabel.ui.home.model

import androidx.annotation.StringRes
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem

data class HomeSectionHeader(
  @StringRes val textResId: Int,
) : RecyclerViewItem {

  override fun getId(): Any = "ITEM_SECTION_HEADER$textResId"
}
