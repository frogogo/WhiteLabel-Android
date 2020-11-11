package ru.poprobuy.poprobuy.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import ru.poprobuy.poprobuy.databinding.ViewErrorStateBinding
import ru.poprobuy.poprobuy.extension.layoutInflater
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener

typealias OnRefreshClickAction = () -> Unit

class ErrorStateView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private var onRefreshClickListener: OnRefreshClickAction? = null

  private val binding = ViewErrorStateBinding.inflate(layoutInflater, this)

  init {
    binding.root.setOnSafeClickListener { onRefreshClickListener?.invoke() }
  }

  fun setOnRefreshClickListener(listener: OnRefreshClickAction) {
    onRefreshClickListener = listener
  }

}
