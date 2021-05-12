package ru.frogogo.whitelabel.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
  @Px private val horizontalSpacing: Int = 0,
  @Px private val verticalSpacing: Int = horizontalSpacing,
  @Px private val topSpacing: Int = verticalSpacing,
  @Px private val bottomSpacing: Int = verticalSpacing,
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    val params = getItemParams(view, parent, state)

    outRect.top = if (params.isFirstSpanGroup()) {
      topSpacing
    } else {
      verticalSpacing
    }

    outRect.left = if (params.isFirstSpanItem()) {
      horizontalSpacing
    } else {
      // Add half spacing to respect proportions on both sides
      horizontalSpacing / 2
    }

    outRect.right = if (params.isLastSpanItem()) {
      horizontalSpacing
    } else {
      // Add half spacing to respect proportions on both sides
      horizontalSpacing / 2
    }

    outRect.bottom = if (params.isLastSpanGroup()) {
      bottomSpacing
    } else {
      0
    }
  }

  private fun getItemParams(view: View, parent: RecyclerView, state: RecyclerView.State): ItemParams {
    val layoutManager = parent.layoutManager
    val itemPosition = parent.getChildViewHolder(view).adapterPosition

    when (layoutManager) {
      is GridLayoutManager -> {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val clampedSpanCount = layoutManager.getClampedSpanCount()
        val spanSize = layoutParams.spanSize
        val spanIndex = layoutManager.getSpanIndex(itemPosition, layoutParams)
        val spanGroupIndex = layoutManager.getSpanGroupIndex(itemPosition)
        val spanGroups = layoutManager.getSpanGroupsCount(state.itemCount)

        return ItemParams(
          spanCount = clampedSpanCount,
          spanSize = spanSize,
          spanIndex = spanIndex,
          spanGroupIndex = spanGroupIndex,
          spanGroups = spanGroups,
        )
      }
      is LinearLayoutManager -> {
        return ItemParams(
          spanCount = 1,
          spanSize = 1,
          spanIndex = 0,
          spanGroupIndex = itemPosition,
          spanGroups = parent.adapter?.itemCount ?: 0
        )
      }
      null -> error("RecyclerView without layout manager")
      else -> error("Unsupported layout manager: ${layoutManager::class.java.simpleName}")
    }
  }

  private fun GridLayoutManager.getSpanIndex(
    itemPosition: Int,
    layoutParams: GridLayoutManager.LayoutParams,
  ): Int = if (itemPosition == RecyclerView.NO_POSITION) {
    // No need to query span index for item without position
    // (deleted item which is being animated)
    layoutParams.spanIndex
  } else {
    spanSizeLookup.getSpanIndex(itemPosition, spanCount)
  }

  private fun GridLayoutManager.getClampedSpanCount(): Int =
    spanCount.coerceAtLeast(1)

  private fun GridLayoutManager.getSpanGroupIndex(itemPosition: Int): Int =
    spanSizeLookup.getSpanGroupIndex(itemPosition, getClampedSpanCount())

  private fun GridLayoutManager.getSpanGroupsCount(itemCount: Int): Int {
    val spanSizeLookup = spanSizeLookup
    val lastItemIndex = if (reverseLayout) 0 else itemCount - 1

    return spanSizeLookup.getSpanGroupIndex(lastItemIndex, getClampedSpanCount()) + 1
  }

  private data class ItemParams(
    // Total span count in layout
    val spanCount: Int,
    // Span size of current item
    val spanSize: Int,
    // Span index in current item's span group
    val spanIndex: Int,
    // Row index
    val spanGroupIndex: Int,
    // Rows amount
    val spanGroups: Int,
  ) {

    fun isFirstSpanGroup(): Boolean =
      spanGroupIndex == 0

    fun isLastSpanGroup(): Boolean =
      spanGroupIndex == spanGroups - 1

    fun isFirstSpanItem(): Boolean =
      spanIndex == 0

    fun isLastSpanItem(): Boolean = when {
      // Checks whether item consumes all span size
      spanCount == spanSize -> true
      // Checks whether item has normal size and at last position
      spanCount - 1 == spanIndex -> true
      else -> false
    }
  }
}
