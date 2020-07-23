package ru.poprobuy.poprobuy.arch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.arch.navigation.NavigationRouter

abstract class BaseBottomSheetDialogFragment<out T : BaseViewModel>(
  @LayoutRes private val layoutResId: Int
) : BottomSheetDialogFragment() {

  abstract val viewModel: T

  private val navigationRouter: NavigationRouter by inject { parametersOf(requireActivity(), findNavController()) }

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

  @MainThread
  protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T) -> Unit) {
    observe(viewLifecycleOwner, onChanged)
  }

  /**
   * Handles navigation events from a [ViewModel]
   */
  private fun initInnerObservers() = viewModel.run {
    navigationLiveEvent.observe(navigationRouter::navigate)
  }

}
