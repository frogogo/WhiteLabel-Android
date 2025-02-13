package ru.frogogo.whitelabel.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import com.google.android.material.textfield.TextInputEditText

class ClearFocusEditText : TextInputEditText {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
    // Clear focus on back button click
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      clearFocus()
    }

    return super.onKeyPreIme(keyCode, event)
  }
}
