package ru.frogogo.whitelabel.ui.home

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeCouponProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ItemHomeCouponProgressBinding
import ru.frogogo.whitelabel.databinding.ItemHomeReceiptBinding
import ru.frogogo.whitelabel.databinding.ItemHomeSectionHeaderBinding
import ru.frogogo.whitelabel.extension.toDateTime
import ru.frogogo.whitelabel.ui.home.model.HomeSectionHeader
import ru.frogogo.whitelabel.util.PriceUtils

object HomeAdapterDelegates {

//  fun emptyStateDelegate(
//    scanReceiptAction: () -> Unit,
//  ) = adapterDelegateViewBinding<HomeState.Empty, RecyclerViewItem, ItemHomeEmptyBinding>(
//    viewBinding = { layoutInflater, root -> ItemHomeEmptyBinding.inflate(layoutInflater, root, false) }
//  ) {
//    binding.buttonScan.setOnSafeClickListener { scanReceiptAction() }
//  }

  fun couponProgressDelegate() =
    adapterDelegateViewBinding<HomeCouponProgressUiModel, RecyclerViewItem, ItemHomeCouponProgressBinding>(
      viewBinding = { layoutInflater, root -> ItemHomeCouponProgressBinding.inflate(layoutInflater, root, false) }
    ) {
      bind {
        binding.textViewProgressCurrent.text = PriceUtils.formatPrice(item.progressCurrent)
        binding.textViewProgressTarget.text = PriceUtils.formatPrice(item.progressTarget)
        binding.progressBar.apply {
          max = item.progressTarget
          progress = item.progressCurrent
        }
        binding.textViewNotice.text = item.notice
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

  fun receiptDelegate() = adapterDelegateViewBinding<ReceiptUiModel, RecyclerViewItem, ItemHomeReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeReceiptBinding.inflate(layoutInflater, root, false) }
  ) {

    bind {
      binding.textViewReceiptValue.text = PriceUtils.formatPrice(item.value)
      binding.textViewDate.text = item.date.toDateTime()
      binding.imageViewStatus.setImageResource(item.state.getStatusIcon())
    }
  }
}
