package ru.frogogo.whitelabel.core.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import ru.frogogo.whitelabel.R

private const val DIM_AMOUNT = 0.65F

open class BaseDialogFragment(
  @LayoutRes private val layoutId: Int,
  private val showBackground: Boolean = true,
) : DialogFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setStyle(STYLE_NORMAL, R.style.DialogFragment)
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)

    requireDialog().window?.apply {
      if (showBackground) {
        setBackgroundDrawableResource(R.drawable.background_dialog)
      } else {
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      }
      setDimAmount(DIM_AMOUNT)
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
