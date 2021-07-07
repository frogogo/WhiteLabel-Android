@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.extension.loadBindingModule
import ru.frogogo.whitelabel.extension.scopedQualifier
import ru.frogogo.whitelabel.ui.promotion_items.PromotionItemsEffect
import ru.frogogo.whitelabel.ui.promotion_items.PromotionItemsFragment
import ru.frogogo.whitelabel.ui.promotion_items.PromotionItemsViewModel
import ru.frogogo.whitelabel.ui.promotion_items.data.PromotionItemsDataFactory
import ru.frogogo.whitelabel.ui.promotion_items.data.PromotionItemsDataFactoryImpl
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsDataLoadDelegate

private const val NAMED_PROMOTION = "promotion"
private const val NAMED_CONTENT_LIVE = "data_live"
private const val NAMED_EFFECT_EVENT = "effect_event"

fun Module.promotionItems() {
  scope<PromotionItemsFragment> {
    // Core
    viewModel { params ->
      loadBindingModule {
        single(scopedQualifier(NAMED_PROMOTION)) { params.get<HomePromotionUiModel>() }
      }

      PromotionItemsViewModel(get(), get())
    }

    // LiveData
    scoped(named(NAMED_CONTENT_LIVE)) { MutableLiveData<List<RecyclerViewItem>>() }
    scoped(named(NAMED_EFFECT_EVENT)) { LiveEvent<PromotionItemsEffect>() }
    scoped {
      PromotionItemsViewModel.LiveDataHolder(
        mutableDataLive = getDataLive(),
        mutableEffectLiveEvent = getEffectEvent(),
      )
    }

    // Delegates
    scoped {
      PromotionItemsDataLoadDelegate(
        dispatchersProvider = get(),
        promotion = getPromotion(),
        mutableDataLive = getDataLive(),
        getItemsUseCase = get(),
        dataFactory = get(),
      )
    }
    scoped {
      PromotionItemsClicksHandlerDelegateImpl(get())
    } bind PromotionItemsClicksHandlerDelegate::class
    scoped { PromotionItemsViewModel.DelegatesHolder(get(), get()) }

    // Data
    scoped { PromotionItemsDataFactoryImpl() as PromotionItemsDataFactory }
  }
}

private fun Scope.getPromotion(): HomePromotionUiModel =
  get(scopedQualifier(NAMED_PROMOTION))

private fun Scope.getEffectEvent(): LiveEvent<PromotionItemsEffect> =
  get(named(NAMED_EFFECT_EVENT))

private fun Scope.getDataLive(): MutableLiveData<List<RecyclerViewItem>> =
  get(named(NAMED_CONTENT_LIVE))
