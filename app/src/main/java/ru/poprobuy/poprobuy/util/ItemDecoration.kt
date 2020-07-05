package ru.poprobuy.poprobuy.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
  @Px private val verticalSpacing: Int,
  @Px private val horizontalSpacing: Int
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    if (parent.getChildAdapterPosition(view) < 0) {
      outRect.setEmpty()
      return
    }

    val params = getItemParams(view, parent)
    val isFirstSpanItem = params.spanIndex == 0
    val isLastSpanItem = when {
      // Checks whether item consumes all span size
      params.spanCount == params.spanSize -> true
      // Checks whether item has normal size and at last position
      params.spanCount - 1 == params.spanIndex -> true
      else -> false
    }

    outRect.top = verticalSpacing
    outRect.left = when {
      isFirstSpanItem -> horizontalSpacing
      else -> horizontalSpacing / 2
    }
    outRect.right = when {
      isLastSpanItem -> horizontalSpacing
      else -> horizontalSpacing / 2
    }
  }

  private fun getItemParams(view: View, parent: RecyclerView): ItemParams {
    val layoutManager = parent.layoutManager
    val itemPosition = parent.getChildAdapterPosition(view)

    when (layoutManager) {
      is GridLayoutManager -> {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val clampedSpanCount = layoutManager.spanCount.coerceAtLeast(1)
        val spanSize = layoutManager.spanSizeLookup.getSpanSize(itemPosition)
        val spanIndex = layoutParams.spanIndex

        return ItemParams(
          spanCount = clampedSpanCount,
          spanSize = spanSize,
          spanIndex = spanIndex
        )
      }
      is LinearLayoutManager -> {
        return ItemParams(
          spanCount = 1,
          spanSize = 1,
          spanIndex = 0
        )
      }
      null -> throw IllegalArgumentException("RecyclerView without layout manager")
      else -> throw IllegalArgumentException("Unsupported layout manager: ${layoutManager::class.java.simpleName}")
    }
  }

  private data class ItemParams(
    // Total span count in layout
    val spanCount: Int,
    // Span size of current item
    val spanSize: Int,
    // span index in current item's span group
    val spanIndex: Int
  )

}
