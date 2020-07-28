package ru.poprobuy.poprobuy.util

import android.content.Context
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.view.ClickableSpanKtx

object SpannableUtils {

  /**
   * @return spanned string with "Something went wrong! Repeat" text
   * where "Repeat" part is clickable and executes [clickCallback]
   */
  fun createSomethingWentWrongSpan(context: Context, clickCallback: () -> Unit): Spanned = buildSpannedString {
    val textMain = context.getString(R.string.error_something_went_wrong)
    val colorMain = context.getColor(R.color.gray_500)
    color(colorMain) {
      append(textMain)
    }

    append(" ".repeat(2))

    val textButton = context.getString(R.string.error_something_went_wrong_retry)
    val colorButton = context.getColor(R.color.primary_500)
    inSpans(ForegroundColorSpan(colorButton), ClickableSpanKtx(clickCallback)) {
      append(textButton)
    }
  }

}
