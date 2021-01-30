package ru.poprobuy.poprobuy.util

import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager

abstract class BaseWindowAnimator(private val window: Window) {

  protected abstract val insetsListener: View.OnApplyWindowInsetsListener

  init {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
  }

  fun start() {
    window.decorView.setOnApplyWindowInsetsListener(insetsListener)
  }

  fun stop() {
    window.decorView.setOnApplyWindowInsetsListener(null)
  }
}

class SimpleWindowAnimator(window: Window) : BaseWindowAnimator(window) {

  private val sceneRoot: ViewGroup? by unsafeLazy {
    window.decorView.findViewById<View>(Window.ID_ANDROID_CONTENT)?.parent as? ViewGroup
  }

  override val insetsListener: View.OnApplyWindowInsetsListener
    get() = View.OnApplyWindowInsetsListener { view, insets ->
      sceneRoot?.let { scene -> TransitionManager.beginDelayedTransition(scene, ChangeBounds()) }
      return@OnApplyWindowInsetsListener view.onApplyWindowInsets(insets)
    }
}
