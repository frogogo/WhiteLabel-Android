package ru.frogogo.whitelabel.ui.common

import androidx.core.text.buildSpannedString
import androidx.core.text.color
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.databinding.ItemCouponBinding
import ru.frogogo.whitelabel.databinding.ItemItemBinding
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.util.PriceUtils

typealias CouponClickAction = (CouponUiModel) -> Unit

object CommonAdapterDelegates {

  fun couponDelegate(onClickAction: CouponClickAction) =
    adapterDelegateViewBinding<CouponUiModel, RecyclerViewItem, ItemCouponBinding>(
      viewBinding = { layoutInflater, viewGroup -> ItemCouponBinding.inflate(layoutInflater, viewGroup, false) },
    ) {
      itemView.setSafeOnClickListener { onClickAction(item) }

      bind {
        binding.imageViewCoupon.load(item.photo.thumbUrl)
        binding.textViewId.text = getString(R.string.coupon_number, item.id)
        binding.textViewName.text = item.name
      }
    }

  fun itemDelegate() = adapterDelegateViewBinding<ItemUiModel, RecyclerViewItem, ItemItemBinding>(
    viewBinding = { layoutInflater, viewGroup -> ItemItemBinding.inflate(layoutInflater, viewGroup, false) },
  ) {
    bind {
      binding.imageView.load(item.imageUrl)
      binding.textViewName.text = buildSpannedString {
        append(item.name)
        if (item.specs != null) {
          append(' ')
          color(context.getColor(R.color.gray_300)) {
            append(item.specs)
          }
        }
      }
      binding.textViewPrice.text = PriceUtils.formatPrice(item.price)
      binding.textViewPriceDiscounted.text = PriceUtils.formatPrice(item.discountedPrice)
    }
  }
}
