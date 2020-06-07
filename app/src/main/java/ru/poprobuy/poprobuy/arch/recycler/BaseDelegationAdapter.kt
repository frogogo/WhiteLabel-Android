package ru.poprobuy.poprobuy.arch.recycler

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class BaseDelegationAdapter(
  vararg adapterDelegates: AdapterDelegate<List<RecyclerViewItem>>,
  /**
   * Model for empty state representation
   */
  private val emptyListItem: RecyclerViewItem? = null,
  /**
   * Do not automatically add empty state item when list is empty
   */
  private val manualEmptyStateControl: Boolean = false
) : AsyncListDifferDelegationAdapter<RecyclerViewItem>(DiffUtilCallback) {

  init {
    adapterDelegates.forEach { delegatesManager.addDelegate(it) }
  }

  override fun setItems(items: MutableList<RecyclerViewItem>) {
    this.setItems(items, null)
  }

  fun setItems(items: MutableList<RecyclerViewItem>, commitCallback: (() -> Unit)?) {
    if (!manualEmptyStateControl) {
      emptyListItem?.let {
        if (items.isEmpty()) items.add(it)
      }
    }
    differ.submitList(items, commitCallback)
  }

}
