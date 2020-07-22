package ru.poprobuy.poprobuy.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus.ACCEPTED
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus.CHECK
import ru.poprobuy.poprobuy.extension.layoutInflater
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.extension.toDateTime
import ru.poprobuy.poprobuy.util.PriceUtils

class ReceiptView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding = ViewReceiptBinding.inflate(layoutInflater, this, true)

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

  fun setReceipt(receipt: ReceiptUiModel) = binding.run {
    // Header
    val headerColor = context.getColor(receipt.status.getColor())
    viewHeader.backgroundTintList = ColorStateList.valueOf(headerColor)
    // Id
    textViewReceiptId.text = context.getString(R.string.receipt_id, receipt.id)
    // Value
    textViewReceiptValue.text = PriceUtils.formatPrice(receipt.value)
    // Shop
    textViewShop.text = receipt.shopName
    textViewShop.setVisible(receipt.status != CHECK)
    // Date
    textViewDate.text = receipt.date.toDateTime()
    textViewDate.setTextAppearance(
      if (receipt.status == CHECK) R.style.ReceiptDateText_Big else R.style.ReceiptDateText_Small
    )
    // Status Name
    textViewStatus.text = context.getString(receipt.status.getName())
    // Status Subtitle
    textViewStatusSubtitle.text = receipt.status.getStatusSubtitle(context)
    // Status Icon
    imageViewStatus.setImageResource(receipt.status.getHeaderIcon())
    // Footer
    layoutFooterAccept.root.setVisible(receipt.status == ACCEPTED)
    layoutFooterCheck.root.setVisible(receipt.status == CHECK)
  }

  /**
   * Outline provider for ReceiptView
   */
  private class ReceiptOutlineProvider(private val radius: Float) : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
      val heightModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) 0.99F else 0.97F
      val rect = Rect(
        0,
        0,
        view.width,
        // Make bottom point upper to respect curved footer
        (view.height * heightModifier).toInt()
      )
      // Use radius to respect rounded top corners
      outline.setRoundRect(rect, radius)
    }
  }

}
