package ru.poprobuy.poprobuy.ui.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.extension.setSoftInputMode
import ru.poprobuy.poprobuy.extension.setStatusBarColor

abstract class BaseFragment<out T : BaseViewModel>(
  @LayoutRes layoutId: Int,
  @ColorRes private val statusBarColor: Int = R.color.status_bar,
  private val translucentStatusBar: Boolean = false,
  private val softInputMode: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
) : Fragment(layoutId) {

  abstract val viewModel: T

  open fun initViews() = Unit

  open fun initObservers() = Unit

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Init fragment
    initViews()
    initObservers()

    viewModel.onCreate()
  }

  override fun onStart() {
    setStatusBarColor()
    setTranslucentStatusBar()
    setSoftInputMode(softInputMode)
    super.onStart()
    viewModel.onStart()
  }

  @MainThread
  protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T) -> Unit) {
    observe(viewLifecycleOwner, onChanged)
  }

  private fun setStatusBarColor() {
    requireActivity().setStatusBarColor(requireContext().getColor(statusBarColor))
  }

  private fun setTranslucentStatusBar() {
    if (translucentStatusBar) {
      requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    } else {
      requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
  }
}
