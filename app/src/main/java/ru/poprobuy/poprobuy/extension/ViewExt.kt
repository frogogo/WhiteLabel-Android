package ru.poprobuy.poprobuy.extension

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.annotation.StyleableRes
import androidx.core.view.*
import ru.poprobuy.poprobuy.util.SafeClickListener

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
  visibility = if (visible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}

val View.layoutInflater: LayoutInflater
  get() = LayoutInflater.from(context)

inline fun View.setOnSafeClickListener(
  throttleDuration: Long = 500,
  crossinline clickAction: (View) -> Unit
) {
  setOnClickListener(SafeClickListener(throttleDuration) { v ->
    clickAction(v)
  })
}

/**
 * Used to apply styled attributes in custom view
 */
@SuppressLint("Recycle")
inline fun View.withTypedArray(
  attrs: AttributeSet?,
  @StyleableRes styleable: IntArray,
  block: TypedArray.() -> Unit
) = context.obtainStyledAttributes(attrs, styleable)
  .apply { block() }
  .recycle()

/**
 * Updates this view's margins. This version of the method allows using named parameters
 * to just set one or more axes.
 */
fun View.updateMargin(
  @Px left: Int = marginLeft,
  @Px top: Int = marginTop,
  @Px right: Int = marginRight,
  @Px bottom: Int = marginBottom
) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
  setMargins(left, top, right, bottom)
}

fun View.setSize(newWidth: Int, newHeight: Int) {
  updateLayoutParams {
    width = newWidth
    height = newHeight
  }
}
