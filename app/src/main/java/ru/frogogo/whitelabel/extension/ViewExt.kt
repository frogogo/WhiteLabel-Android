package ru.frogogo.whitelabel.extension

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.annotation.StyleableRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import ru.frogogo.whitelabel.util.SafeClickListener

const val CLICK_THROTTLE_DURATION = 500L

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
  visibility = if (visible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}

fun View.animateToVisible(duration: Long = 500) {
  if (isVisible) {
    return
  }

  alpha = 0F
  setVisible(true)

  animate()
    .alpha(1F)
    .setDuration(duration)
    .start()
}

fun View.animateToGone(duration: Long = 500) {
  if (isGone) {
    return
  }

  alpha = 1F

  animate()
    .alpha(0F)
    .setDuration(duration)
    .withEndAction { setVisible(false) }
    .start()
}

fun View.hideKeyboard() {
  context.getInputMethodManager().hideSoftInputFromWindow(windowToken, 0)
}

inline fun View.setSafeOnClickListener(
  crossinline clickAction: (View) -> Unit,
  throttleDuration: Long = 500,
) {
  setOnClickListener(
    SafeClickListener(throttleDuration) { view ->
      clickAction(view)
    },
  )
}

inline fun View.setSafeOnClickListener(
  crossinline clickAction: () -> Unit,
) {
  setOnClickListener(
    SafeClickListener(CLICK_THROTTLE_DURATION) {
      clickAction.invoke()
    },
  )
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
