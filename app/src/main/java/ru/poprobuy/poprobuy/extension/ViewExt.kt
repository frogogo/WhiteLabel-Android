package ru.poprobuy.poprobuy.extension

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StyleableRes
import ru.poprobuy.poprobuy.util.SafeClickListener

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
  visibility = if (visible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}

val View.layoutInflater: LayoutInflater
  get() = LayoutInflater.from(context)

inline fun View.setOnSafeClickListener(crossinline onSafeClick: (View) -> Unit) {
  setOnClickListener(SafeClickListener { v ->
    onSafeClick(v)
  })
}

inline fun View.setOnSafeClickListener(interval: Int, crossinline onSafeClick: (View) -> Unit) {
  setOnClickListener(SafeClickListener(interval) { v ->
    onSafeClick(v)
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
