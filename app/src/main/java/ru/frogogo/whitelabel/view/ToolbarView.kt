package ru.frogogo.whitelabel.view

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.RecyclerView
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.databinding.ViewToolbarBinding
import ru.frogogo.whitelabel.extension.inflateViewBinding
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.setVisible

class ToolbarView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

  private val binding: ViewToolbarBinding = inflateViewBinding()

  init {
    stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.toolbar_elevation)
    setBackgroundColor(context.getColor(R.color.screen_background))
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      outlineSpotShadowColor = context.getColor(R.color.elevation_shadow)
    }

    context.withStyledAttributes(attrs, R.styleable.ToolbarView) {
      getString(R.styleable.ToolbarView_toolbar_title)?.let { title ->
        binding.textViewTitle.apply {
          setVisible(true)
          text = title
        }
      }
      getDrawable(R.styleable.ToolbarView_toolbar_actionIcon)?.let { icon ->
        binding.buttonAction.apply {
          setVisible(true)
          setImageDrawable(icon)
        }
      }
    }
  }

  fun setTitle(title: String) {
    binding.textViewTitle.text = title
  }

  fun setBackButtonListener(onClickAction: () -> Unit) {
    binding.buttonBack.apply {
      setVisible(true)
      setOnSafeClickListener { onClickAction() }
    }
  }

  fun setActionButtonListener(onClickAction: () -> Unit) {
    binding.buttonAction.apply {
      setVisible(true)
      setOnSafeClickListener { onClickAction() }
    }
  }

  fun attachToRecyclerView(recyclerView: RecyclerView) {
    recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
      isSelected = recyclerView.canScrollVertically(-1)
    }
  }
}
