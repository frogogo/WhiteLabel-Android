package ru.poprobuy.poprobuy.arch.recycler

import com.github.ajalt.timberkt.d
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class BaseDelegationAdapter(
  vararg adapterDelegates: AdapterDelegate<List<RecyclerViewItem>>,
  /**
   * Model for empty state representation
   */
  private val emptyListItem: RecyclerViewItem? = null
) : AsyncListDifferDelegationAdapter<RecyclerViewItem>(DiffUtilCallback) {

  init {
    adapterDelegates.forEach { delegatesManager.addDelegate(it) }
  }

  override fun setItems(items: MutableList<RecyclerViewItem>) {
    this.setItems(items, null)
  }

  fun setItems(items: MutableList<RecyclerViewItem>, commitCallback: (() -> Unit)?) {
    val itemsMutable = items.toMutableList()
    emptyListItem?.let {
      d { "List is empty, adding empty state" }
      if (itemsMutable.isEmpty()) itemsMutable.add(it)
    }
    differ.submitList(itemsMutable, commitCallback)
  }

}
