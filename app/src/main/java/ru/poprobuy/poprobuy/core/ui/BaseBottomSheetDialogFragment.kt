package ru.poprobuy.poprobuy.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.core.navigation.NavigationRouter
import ru.poprobuy.poprobuy.extension.observeEvent

abstract class BaseBottomSheetDialogFragment<out T : BaseViewModel>(
  @LayoutRes private val layoutResId: Int,
) : BottomSheetDialogFragment() {

  abstract val viewModel: T

  private val navigationRouter: NavigationRouter by inject { parametersOf(findNavController()) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(layoutResId, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initViews()
    initObservers()
    initInnerObservers()
  }

  open fun initViews(): Unit = Unit

  open fun initObservers(): Unit = Unit

  /**
   * Handles navigation events from a [ViewModel]
   */
  private fun initInnerObservers() {
    observeEvent(viewModel.navigationLiveEvent, navigationRouter::navigate)
  }

}
