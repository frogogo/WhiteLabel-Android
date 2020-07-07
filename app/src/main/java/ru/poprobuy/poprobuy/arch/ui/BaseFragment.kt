package ru.poprobuy.poprobuy.arch.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationRouter
import ru.poprobuy.poprobuy.extension.setFullScreen
import ru.poprobuy.poprobuy.extension.setStatusBarColor
import ru.poprobuy.poprobuy.extension.setStatusBarLight
import ru.poprobuy.poprobuy.util.SimpleWindowAnimator

abstract class BaseFragment<out T : BaseViewModel>(
  @LayoutRes layoutId: Int,
  @ColorRes private val statusBarColor: Int = R.color.status_bar,
  private val fullscreen: Boolean = false,
  /**
   * Enables window inset animations
   */
  private val windowAnimations: Boolean = false,
  /**
   * Determines status bar color state.
   * Should be true if status bar color is light and status bar content should be dark
   */
  private val lightStatusBar: Boolean = true
) : Fragment(layoutId) {

  abstract val viewModel: T

  private val windowAnimator: SimpleWindowAnimator by lazy { SimpleWindowAnimator(requireActivity().window) }

  open fun initViews() = Unit

  open fun initObservers() = Unit

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Init fragment
    initViews()
    initObservers()
    observeNavigation()

    viewModel.onCreate()
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()

    // Status bar theme
    requireActivity().setStatusBarLight(lightStatusBar)
    // Status bar color
    requireActivity().setStatusBarColor(requireContext().getColor(statusBarColor))
    // Fullscreen
    requireActivity().setFullScreen(fullscreen)
    // Inset animations
    if (windowAnimations) {
      windowAnimator.start()
    } else {
      windowAnimator.stop()
    }
  }

  override fun onStop() {
    super.onStop()
    viewModel.onStop()
  }

  @MainThread
  protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T) -> Unit) {
    observe(viewLifecycleOwner, onChanged)
  }

  /**
   * Handles navigation events from a [ViewModel]
   */
  private fun observeNavigation() {
    viewModel.navigationLive.observe {
      NavigationRouter.navigate(findNavController(), it)
    }
  }

}
