package ru.poprobuy.poprobuy.arch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import ru.poprobuy.poprobuy.R

open class BaseDialogFragment(
  @LayoutRes private val layoutId: Int,
) : DialogFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setStyle(STYLE_NORMAL, R.style.DialogFragment)
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)

    requireDialog().window?.apply {
      setBackgroundDrawableResource(R.drawable.background_dialog)
      setDimAmount(0.65F)
    }

    return inflater.inflate(layoutId, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
    initObservers()
  }

  open fun initViews(): Unit = Unit

  open fun initObservers(): Unit = Unit

}
