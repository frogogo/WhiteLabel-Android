package ru.poprobuy.poprobuy.core.recycler

import com.github.ajalt.timberkt.d
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class BaseDelegationAdapter(
  vararg adapterDelegates: AdapterDelegate<List<RecyclerViewItem>>,
  /**
   * Model for empty state representation
   */
  private val emptyListItem: RecyclerViewItem? = null,
) : AsyncListDifferDelegationAdapter<RecyclerViewItem>(DiffUtilCallback) {

  init {
    adapterDelegates.forEach { delegate -> delegatesManager.addDelegate(delegate) }
  }

  override fun setItems(items: MutableList<RecyclerViewItem>) {
    this.setItems(items, null)
  }

  fun setItems(items: MutableList<RecyclerViewItem>, commitCallback: (() -> Unit)?) {
    val itemsMutable = items.toMutableList()
    emptyListItem?.let { emptyItem ->
      d { "List is empty, adding empty state" }
      itemsMutable.ifEmpty { itemsMutable.add(emptyItem) }
    }
    differ.submitList(itemsMutable, commitCallback)
  }
}
