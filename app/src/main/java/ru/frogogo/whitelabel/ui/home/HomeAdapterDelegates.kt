package ru.frogogo.whitelabel.ui.home

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeProgressUiModel
import ru.frogogo.whitelabel.databinding.ItemHomeEmptyBinding
import ru.frogogo.whitelabel.databinding.ItemHomeEmptyInstructionsBinding
import ru.frogogo.whitelabel.databinding.ItemHomeProgressBinding
import ru.frogogo.whitelabel.databinding.ItemHomeReceiptsButtonBinding
import ru.frogogo.whitelabel.databinding.ItemHomeScanUnavailableBinding
import ru.frogogo.whitelabel.databinding.ItemHomeSectionHeaderBinding
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.ui.home.model.HomeEmptyState
import ru.frogogo.whitelabel.ui.home.model.HomeInstructions
import ru.frogogo.whitelabel.ui.home.model.HomeReceiptsButton
import ru.frogogo.whitelabel.ui.home.model.HomeScanUnavailable
import ru.frogogo.whitelabel.ui.home.model.HomeSectionHeader
import ru.frogogo.whitelabel.util.PriceUtils

typealias OnReceiptsClickAction = () -> Unit

object HomeAdapterDelegates {

  fun emptyStateDelegate() = adapterDelegateViewBinding<HomeEmptyState, RecyclerViewItem, ItemHomeEmptyBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeEmptyBinding.inflate(layoutInflater, root, false) },
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
      viewBinding = { layoutInflater, root -> ItemHomeProgressBinding.inflate(layoutInflater, root, false) },
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
      viewBinding = { layoutInflater, root -> ItemHomeSectionHeaderBinding.inflate(layoutInflater, root, false) },
    ) {
      bind {
        binding.root.setText(item.textResId)
      }
    }

  fun receiptsButtonDelegate(receiptsClickAction: OnReceiptsClickAction) =
    adapterDelegateViewBinding<HomeReceiptsButton, RecyclerViewItem, ItemHomeReceiptsButtonBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeReceiptsButtonBinding.inflate(layoutInflater, root, false) },
    ) {
      itemView.setSafeOnClickListener { receiptsClickAction.invoke() }
    }

  fun scanUnavailableDelegate() =
    adapterDelegateViewBinding<HomeScanUnavailable, RecyclerViewItem, ItemHomeScanUnavailableBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeScanUnavailableBinding.inflate(layoutInflater, root, false) },
    ) {
      /* no-op */
    }

  fun instructionsDelegate() =
    adapterDelegateViewBinding<HomeInstructions, RecyclerViewItem, ItemHomeEmptyInstructionsBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeEmptyInstructionsBinding.inflate(layoutInflater, root, false) },
    ) {
      /* no-op */
    }
}
