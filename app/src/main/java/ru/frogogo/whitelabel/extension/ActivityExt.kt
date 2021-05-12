package ru.frogogo.whitelabel.extension

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.WindowManager

fun Activity.hideKeyboard() {
  var view = currentFocus

  if (view == null) {
    view = findViewById(android.R.id.content)
  }
  if (view == null) {
    view = View(this)
    view.requestFocus()
  }

  view.hideKeyboard()
  view.clearFocus()
}

fun Activity.setStatusBarColor(color: Int) {
  window.apply {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    statusBarColor = color
  }
}

fun Activity.getStatusBarHeight(): Int {
  val rectangle = Rect()
  window.decorView.getWindowVisibleDisplayFrame(rectangle)
  return rectangle.top
}

fun Activity.setStatusBarLight(isLight: Boolean) {
  window.decorView.systemUiVisibility = if (isLight) {
    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
  } else {
    // To unset the flag (and do not set it again if it wasn't set before) we should use AND NOT operation (& ~)
    window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
  }
}

fun Activity.setFullScreen(isFullscreen: Boolean) {
  window.apply {
    if (isFullscreen) {
      addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    } else {
      clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
  }

  window.decorView.systemUiVisibility = if (isFullscreen) {
    window.decorView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
  } else {
    // To unset the flags (and do not set it again if it wasn't set before) we should use AND NOT operation (& ~)
    window.decorView.systemUiVisibility and
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv() and
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
  }
}
