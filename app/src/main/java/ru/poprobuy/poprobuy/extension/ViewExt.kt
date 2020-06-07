package ru.poprobuy.poprobuy.extension

import android.view.View

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
  visibility = if (visible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}
