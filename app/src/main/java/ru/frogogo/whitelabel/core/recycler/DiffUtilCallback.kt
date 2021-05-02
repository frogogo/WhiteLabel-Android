package ru.frogogo.whitelabel.core.recycler

import androidx.recyclerview.widget.DiffUtil

object DiffUtilCallback : DiffUtil.ItemCallback<RecyclerViewItem>() {

  override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
    return oldItem.getId() == newItem.getId()
  }

  override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
    return oldItem.equals(newItem)
  }

  override fun getChangePayload(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Any = Unit
}
