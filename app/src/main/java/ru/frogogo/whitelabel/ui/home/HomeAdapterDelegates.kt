package ru.frogogo.whitelabel.ui.home

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.*
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.extension.toDateTime
import ru.frogogo.whitelabel.ui.home.model.HomeEmptyState
import ru.frogogo.whitelabel.ui.home.model.HomeItemsButton
import ru.frogogo.whitelabel.ui.home.model.HomeScanUnavailable
import ru.frogogo.whitelabel.ui.home.model.HomeSectionHeader
import ru.frogogo.whitelabel.util.PriceUtils

typealias OnReceiptClickAction = (ReceiptUiModel) -> Unit
typealias OnItemsButtonClickAction = (HomePromotionUiModel) -> Unit

object HomeAdapterDelegates {

  fun emptyStateDelegate() = adapterDelegateViewBinding<HomeEmptyState, RecyclerViewItem, ItemHomeEmptyBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeEmptyBinding.inflate(layoutInflater, root, false) }
  ) {
    bind {
      binding.imageViewProduct.load(item.promotion.photo.largeUrl)
      binding.textViewProductName.text = item.promotion.name
      binding.textViewPrice.text = PriceUtils.formatPrice(item.promotion.price)
      binding.textViewPriceDiscounted.text = PriceUtils.formatPrice(item.promotion.priceDiscounted)
    }
  }

  fun couponProgressDelegate() =
    adapterDelegateViewBinding<HomeProgressUiModel, RecyclerViewItem, ItemHomeProgressBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeProgressBinding.inflate(layoutInflater, root, false) }
    ) {
      bind {
        binding.textViewProgressCurrent.text = PriceUtils.formatPrice(item.current)
        binding.textViewProgressTarget.text = PriceUtils.formatPrice(item.target)
        binding.progressBar.apply {
          max = item.target
          progress = item.current
        }
      }
    }

  fun sectionHeaderDelegate() =
    adapterDelegateViewBinding<HomeSectionHeader, RecyclerViewItem, ItemHomeSectionHeaderBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeSectionHeaderBinding.inflate(layoutInflater, root, false) }
    ) {
      bind {
        binding.root.setText(item.textResId)
      }
    }

  fun receiptDelegate(receiptClickAction: OnReceiptClickAction) =
    adapterDelegateViewBinding<ReceiptUiModel, RecyclerViewItem, ItemHomeReceiptBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeReceiptBinding.inflate(layoutInflater, root, false) }
    ) {
      itemView.setSafeOnClickListener { receiptClickAction.invoke(item) }

      bind {
        binding.textViewReceiptValue.text = PriceUtils.formatPrice(item.value)
        binding.textViewDate.text = item.date.toDateTime()
        binding.imageViewStatus.setImageResource(item.state.getStatusIcon())
      }
    }

  fun scanUnavailableDelegate() =
    adapterDelegateViewBinding<HomeScanUnavailable, RecyclerViewItem, ItemHomeScanUnavailableBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeScanUnavailableBinding.inflate(layoutInflater, root, false) }
    ) {
      /* no-op */
    }

  fun itemsButtonDelegate(clickAction: OnItemsButtonClickAction) =
    adapterDelegateViewBinding<HomeItemsButton, RecyclerViewItem, ItemHomeItemsButtonBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeItemsButtonBinding.inflate(layoutInflater, root, false) }
    ) {
      itemView.setSafeOnClickListener { clickAction.invoke(item.promotion) }
    }
}
