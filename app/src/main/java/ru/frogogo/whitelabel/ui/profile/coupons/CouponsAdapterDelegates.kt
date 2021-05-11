package ru.frogogo.whitelabel.ui.profile.coupons

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.databinding.ItemCouponsEmptyStateBinding
import ru.frogogo.whitelabel.ui.profile.coupons.model.CouponsEmptyState

object CouponsAdapterDelegates {

  fun emptyState() = adapterDelegateViewBinding<CouponsEmptyState, RecyclerViewItem, ItemCouponsEmptyStateBinding>(
    viewBinding = { layoutInflater, viewGroup ->
      ItemCouponsEmptyStateBinding.inflate(layoutInflater, viewGroup, false)
    }
  ) {
    /* no-op */
  }
}
