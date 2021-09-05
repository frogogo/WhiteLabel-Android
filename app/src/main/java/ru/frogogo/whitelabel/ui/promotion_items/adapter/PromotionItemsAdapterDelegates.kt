package ru.frogogo.whitelabel.ui.promotion_items.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.databinding.ItemPromotionItemsRuleBinding
import ru.frogogo.whitelabel.ui.promotion_items.model.PromotionItemsRule

object PromotionItemsAdapterDelegates {

  fun ruleDelegate() = adapterDelegateViewBinding<PromotionItemsRule, RecyclerViewItem, ItemPromotionItemsRuleBinding>(
    viewBinding = { layoutInflater, viewGroup ->
      ItemPromotionItemsRuleBinding.inflate(layoutInflater, viewGroup, false)
    },
  ) {
    /* no-op */
  }
}
