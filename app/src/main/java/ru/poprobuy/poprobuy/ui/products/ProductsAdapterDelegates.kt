package ru.poprobuy.poprobuy.ui.products

import coil.api.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.databinding.ItemProductBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.binding.setProductState

typealias OnProductClickAction = (ProductUiModel) -> Unit

object ProductsAdapterDelegates {

  private const val ALPHA_ACTIVE = 1F
  private const val ALPHA_INACTIVE = 0.5F

  fun productDelegate(
    clickAction: OnProductClickAction
  ) = adapterDelegateViewBinding<ProductUiModel, RecyclerViewItem, ItemProductBinding>(
    viewBinding = { layoutInflater, root -> ItemProductBinding.inflate(layoutInflater, root, false) }
  ) {

    itemView.setOnSafeClickListener { if (item.isActive()) clickAction(item) }

    bind {
      itemView.isClickable = item.isActive()
      binding.apply {
        imageViewProduct.load(item.imageUrl) {
          placeholder(R.drawable.ic_placeholder)
        }
        textViewName.text = item.name

        // Make content half transparent in disabled state
        listOf(imageViewProduct, textViewName).forEach { view ->
          view.alpha = if (item.isActive()) ALPHA_ACTIVE else ALPHA_INACTIVE
        }

        // Label
        viewLabel.setProductState(item)
      }
    }
  }

}
