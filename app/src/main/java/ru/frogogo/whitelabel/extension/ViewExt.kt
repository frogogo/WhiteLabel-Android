package ru.frogogo.whitelabel.extension

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.annotation.StyleableRes
import androidx.core.view.*
import ru.frogogo.whitelabel.util.SafeClickListener

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
  visibility = if (visible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}

fun View.hideKeyboard() {
  context.getInputMethodManager().hideSoftInputFromWindow(windowToken, 0)
}

inline fun View.setOnSafeClickListener(
  crossinline clickAction: (View) -> Unit,
  throttleDuration: Long = 500,
) {
  setOnClickListener(SafeClickListener(throttleDuration) { view ->
    clickAction(view)
  })
}

inline fun View.setOnSafeClickListener(crossinline clickAction: () -> Unit) {
  setOnClickListener(SafeClickListener(500) {
    clickAction.invoke()
  })
}

inline fun View.setOnClickListener(crossinline clickAction: () -> Unit) {
  setOnClickListener { clickAction.invoke() }
}

/**
 * Used to apply styled attributes in custom view
 */
@SuppressLint("Recycle")
inline fun View.withTypedArray(
  attrs: AttributeSet?,
  @StyleableRes styleable: IntArray,
  block: TypedArray.() -> Unit,
) {
  context.obtainStyledAttributes(attrs, styleable)
    .apply(block)
    .recycle()
}

/**
 * Updates this view's margins. This version of the method allows using named parameters
 * to just set one or more axes.
 */
fun View.updateMargin(
  @Px left: Int = marginLeft,
  @Px top: Int = marginTop,
  @Px right: Int = marginRight,
  @Px bottom: Int = marginBottom,
) {
  updateLayoutParams<ViewGroup.MarginLayoutParams> {
    setMargins(left, top, right, bottom)
  }
}

fun View.setSize(
  newWidth: Int = height,
  newHeight: Int = width,
) {
  updateLayoutParams {
    width = newWidth
    height = newHeight
  }
}
