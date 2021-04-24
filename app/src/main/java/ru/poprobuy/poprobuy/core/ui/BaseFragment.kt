package ru.poprobuy.poprobuy.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import app.cash.exhaustive.Exhaustive
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.navigation.NavigationRouter
import ru.poprobuy.poprobuy.extension.*
import ru.poprobuy.poprobuy.util.SimpleWindowAnimator
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen
import ru.poprobuy.poprobuy.util.analytics.lowercaseName
import ru.poprobuy.poprobuy.util.unsafeLazy

abstract class BaseFragment<out T : BaseViewModel> : Fragment() {

  abstract val viewModel: T

  private val windowAnimator: SimpleWindowAnimator by unsafeLazy { SimpleWindowAnimator(requireActivity().window) }
  private val navigationRouter: NavigationRouter by inject { parametersOf(findNavController()) }
  private val analytics: AnalyticsManager by inject()
  private val configuration: Configuration by lazy { provideConfiguration() }

  abstract fun provideConfiguration(): Configuration

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    applyConfiguration()
    return inflater.inflate(configuration.layoutId, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Init fragment
    initViews()
    initObservers()
    initInnerObservers()

    viewModel.onCreate()
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()
    // Inset animations
    if (configuration.windowAnimations) {
      windowAnimator.start()
    } else {
      windowAnimator.stop()
    }
  }

  override fun onStop() {
    super.onStop()
    viewModel.onStop()
  }

  override fun onResume() {
    super.onResume()
    // Track fragment
    analytics.trackScreen(
      screenName = configuration.screen.lowercaseName(),
      className = javaClass.simpleName
    )
  }

  override fun onDestroyView() {
    (view as? ViewGroup)?.let(this::unsubscribeAdapters)
    super.onDestroyView()
  }

  open fun initViews(): Unit = Unit

  open fun initObservers(): Unit = Unit

  open fun hideKeyboard() {
    requireActivity().hideKeyboard()
  }

  /**
   * Handles navigation events from a [ViewModel]
   */
  private fun initInnerObservers() {
    with(viewModel) {
      observeEvent(navigationLiveEvent, navigationRouter::navigate)
      observeEvent(baseCommandLiveEvent, ::handleCommand)
    }
  }

  private fun handleCommand(command: BaseCommand) {
    @Exhaustive
    when (command) {
      BaseCommand.HideKeyboard -> hideKeyboard()
    }
  }

  private fun unsubscribeAdapters(viewGroup: ViewGroup) {
    viewGroup.children.forEach { view ->
      when (view) {
        is RecyclerView -> view.adapter = null
        is ViewGroup -> unsubscribeAdapters(view)
      }
    }
  }

  private fun applyConfiguration() {
    requireActivity().apply {
      // Status bar theme
      setStatusBarLight(configuration.lightStatusBar)
      // Status bar color
      setStatusBarColor(requireContext().getColor(configuration.statusBarColor))
      // Fullscreen
      setFullScreen(configuration.fullscreen)
    }
  }

  data class Configuration(
    @LayoutRes val layoutId: Int,
    val screen: AnalyticsScreen,
    @ColorRes val statusBarColor: Int = R.color.status_bar,
    val fullscreen: Boolean = false,
    /**
     * Enables window inset animations
     */
    val windowAnimations: Boolean = false,
    /**
     * Determines status bar color state.
     * Should be true if status bar color is light and status bar content should be dark
     */
    val lightStatusBar: Boolean = true,
  )
}
