package ru.poprobuy.poprobuy.extension

import android.app.Activity
import android.view.WindowManager

fun Activity.setStatusBarColor(color: Int) {
  window.apply {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    statusBarColor = color
  }
}
