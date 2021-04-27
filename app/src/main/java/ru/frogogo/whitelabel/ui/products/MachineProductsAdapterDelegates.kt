package ru.frogogo.whitelabel.ui.products

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.machine.VendingCellUiModel
import ru.frogogo.whitelabel.databinding.ItemProductBinding
import ru.frogogo.whitelabel.extension.binding.setProductState
import ru.frogogo.whitelabel.extension.setOnSafeClickListener

typealias OnProductClickAction = (VendingCellUiModel) -> Unit

object ProductsAdapterDelegates {

  private const val ALPHA_ACTIVE = 1F
  private const val ALPHA_INACTIVE = 0.5F

  fun cellDelegate(
    clickAction: OnProductClickAction,
  ) = adapterDelegateViewBinding<VendingCellUiModel, RecyclerViewItem, ItemProductBinding>(
    viewBinding = { layoutInflater, root -> ItemProductBinding.inflate(layoutInflater, root, false) }
  ) {

    itemView.setOnSafeClickListener { if (item.product.isActive()) clickAction(item) }

    bind {
      val product = item.product

      itemView.isClickable = product.isActive()
      binding.apply {
        imageViewProduct.load(product.imageUrl) {
          placeholder(R.drawable.ic_placeholder)
        }
        textViewName.text = product.name

        // Make content half transparent in disabled state
        listOf(imageViewProduct, textViewName).forEach { view ->
          view.alpha = if (product.isActive()) ALPHA_ACTIVE else ALPHA_INACTIVE
        }

        // Label
        viewLabel.setProductState(product)
      }
    }
  }
}
