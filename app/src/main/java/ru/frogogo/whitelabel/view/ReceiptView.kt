package ru.frogogo.whitelabel.view

import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ViewReceiptBinding
import ru.frogogo.whitelabel.extension.binding.bind
import ru.frogogo.whitelabel.extension.inflateViewBinding

private const val HEIGHT_MODIFIER_ANDROID_P = 0.99F
private const val HEIGHT_MODIFIER_ANDROID_OLD = 0.97F

class ReceiptView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: ViewReceiptBinding = inflateViewBinding()

  init {
    // Shadow
    val elevation = resources.getDimensionPixelSize(R.dimen.receipt_elevation).toFloat()
    val radius = resources.getDimensionPixelSize(R.dimen.receipt_top_radius).toFloat()
    outlineProvider = ReceiptOutlineProvider(radius)
    ViewCompat.setElevation(this, elevation)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      outlineSpotShadowColor = context.getColor(R.color.elevation_shadow)
    }
  }

  fun bind(receipt: ReceiptUiModel) = binding.run {
    layoutHeader.bind(receipt)
    // Footer
    layoutFooterApproved.bind(receipt)
    layoutFooterProcessing.bind(receipt)
    layoutFooterRejected.bind(receipt)
  }

  /**
   * Outline provider for ReceiptView
   */
  private class ReceiptOutlineProvider(private val radius: Float) : ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
      val heightModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        HEIGHT_MODIFIER_ANDROID_P
      } else {
        HEIGHT_MODIFIER_ANDROID_OLD
      }
      val rect = Rect(
        0,
        0,
        view.width,
        // Make bottom point upper to respect curved footer
        (view.height * heightModifier).toInt(),
      )
      // Use radius to respect rounded top corners
      outline.setRoundRect(rect, radius)
    }
  }
}
