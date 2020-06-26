package ru.poprobuy.poprobuy.extension

import android.view.View
import ru.poprobuy.poprobuy.util.SafeClickListener

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
  visibility = if (visible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}

inline fun View.setOnSafeClickListener(crossinline onSafeClick: (View) -> Unit) {
  setOnClickListener(SafeClickListener { v ->
    onSafeClick(v)
  })
}

inline fun View.setOnSafeClickListener(interval: Int, crossinline onSafeClick: (View) -> Unit) {
  setOnClickListener(SafeClickListener(interval) { v ->
    onSafeClick(v)
  })
}
