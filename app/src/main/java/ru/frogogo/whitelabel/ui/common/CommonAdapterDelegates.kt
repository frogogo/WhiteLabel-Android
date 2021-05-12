package ru.frogogo.whitelabel.ui.common

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.databinding.ItemCouponBinding
import ru.frogogo.whitelabel.extension.setOnSafeClickListener

typealias CouponClickAction = (CouponUiModel) -> Unit

object CommonAdapterDelegates {

  fun couponDelegate(onClickAction: CouponClickAction) =
    adapterDelegateViewBinding<CouponUiModel, RecyclerViewItem, ItemCouponBinding>(
      viewBinding = { layoutInflater, viewGroup -> ItemCouponBinding.inflate(layoutInflater, viewGroup, false) }
    ) {
      itemView.setOnSafeClickListener { onClickAction(item) }

      bind {
        binding.imageViewCoupon.load(item.photo.thumbUrl)
        binding.textViewId.text = getString(R.string.coupon_number, item.id)
        binding.textViewName.text = item.name
      }
    }
}
