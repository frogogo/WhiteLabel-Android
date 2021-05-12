package ru.frogogo.whitelabel.extension

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.goNext() {
  if (hasNext()) currentItem += 1
}

fun ViewPager2.hasNext(): Boolean {
  val adapter: RecyclerView.Adapter<*> = adapter ?: return false
  return currentItem < adapter.itemCount - 1
}
