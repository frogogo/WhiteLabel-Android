package ru.frogogo.whitelabel.ui.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.data.model.ui.home.HomeProgressUiModel

class HomeOffsetDecoration(
  context: Context,
) : RecyclerView.ItemDecoration() {

  private val offset = context.resources.getDimensionPixelSize(R.dimen.spacing_12)

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    val items = (parent.adapter as? BaseDelegationAdapter)?.items ?: return
    val itemPosition = parent.getChildViewHolder(view).adapterPosition

    val item = items.getOrNull(itemPosition) ?: return

    if (item is HomeProgressUiModel) {
      outRect.top = offset
    }
  }
}
