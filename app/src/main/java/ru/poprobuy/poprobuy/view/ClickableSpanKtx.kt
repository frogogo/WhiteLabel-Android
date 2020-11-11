package ru.poprobuy.poprobuy.view

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * ClickableSpan Kotlin replacement which supports higher-order function
 */
class ClickableSpanKtx(
  private val action: () -> Unit,
  private val underline: Boolean = false,
) : ClickableSpan() {

  override fun updateDrawState(ds: TextPaint) {
    super.updateDrawState(ds)
    ds.isUnderlineText = underline
  }

  override fun onClick(widget: View) {
    action.invoke()
  }

}
