package ru.frogogo.whitelabel.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.extension.fetchDrawable

class LabelView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

  init {
    setPaddings()
    background = context.fetchDrawable(R.drawable.background_label)
  }

  private fun setPaddings() {
    val vertical = resources.getDimensionPixelSize(R.dimen.label_padding_vertical)
    val horizontal = resources.getDimensionPixelSize(R.dimen.label_padding_horizontal)

    setPadding(horizontal, vertical, horizontal, vertical)
  }

  fun setLabelBackground(@ColorInt color: Int) {
    background.mutate().setTint(color)
  }

  fun setIcon(@DrawableRes iconRes: Int) {
    setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0)
  }
}
