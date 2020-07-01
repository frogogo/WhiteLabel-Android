package ru.poprobuy.poprobuy.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat

fun Context.getFloat(@DimenRes id: Int): Float = ResourcesCompat.getFloat(resources, id)

fun Context.showKeyboard(view: View) {
  getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

// Services
fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
