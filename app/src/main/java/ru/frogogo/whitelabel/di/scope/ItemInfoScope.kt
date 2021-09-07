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
import ru.frogogo.whitelabel.extension.loadBindingModule
import ru.frogogo.whitelabel.extension.scopedQualifier
import ru.frogogo.whitelabel.ui.item_info.ItemInfoEffect
import ru.frogogo.whitelabel.ui.item_info.ItemInfoFragment
import ru.frogogo.whitelabel.ui.item_info.ItemInfoViewModel
import ru.frogogo.whitelabel.ui.item_info.data.ItemInfoDataFactory
import ru.frogogo.whitelabel.ui.item_info.data.ItemInfoDataFactoryImpl
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoDataLoadDelegate
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoStateHandlerDelegate

private const val NAMED_ITEM_ID = "promotion"
private const val NAMED_CONTENT_LIVE = "data_live"
private const val NAMED_IS_LOADING_LIVE = "is_loading_live"
private const val NAMED_EFFECT_EVENT = "effect_event"

fun Module.itemInfo() {
  scope<ItemInfoFragment> {
    // Core
    viewModel { params ->
      loadBindingModule {
        single(scopedQualifier(NAMED_ITEM_ID)) { params.get<Int>() }
      }

      ItemInfoViewModel(get(), get())
    }

    // LiveData
    scoped(named(NAMED_CONTENT_LIVE)) { MutableLiveData<List<RecyclerViewItem>>() }
    scoped(named(NAMED_IS_LOADING_LIVE)) { MutableLiveData<Boolean>() }
    scoped(named(NAMED_EFFECT_EVENT)) { LiveEvent<ItemInfoEffect>() }
    scoped {
      ItemInfoViewModel.LiveDataHolder(
        mutableDataLive = getDataLive(),
        mutableIsLoadingLive = getIsLoadingLive(),
        mutableEffectLiveEvent = getEffectEvent(),
      )
    }

    // Delegates
    scoped {
      ItemInfoDataLoadDelegate(
        dispatchersProvider = get(),
        getItemUseCase = get(),
        stateHandlerDelegate = get(),
      )
    }
    scoped {
      ItemInfoStateHandlerDelegate(
        dispatchersProvider = get(),
        itemId = getItemId(),
        mutableDataLive = getDataLive(),
        mutableIsLoadingLive = getIsLoadingLive(),
        mutableEffectLiveEvent = getEffectEvent(),
        dataFactory = get(),
      )
    }
    scoped {
      ItemInfoClicksHandlerDelegateImpl(get())
    } bind ItemInfoClicksHandlerDelegate::class
    scoped { ItemInfoViewModel.DelegatesHolder(get(), get(), get()) }

    // Data
    scoped { ItemInfoDataFactoryImpl() as ItemInfoDataFactory }
  }
}

private fun Scope.getItemId(): Int =
  get(scopedQualifier(NAMED_ITEM_ID))

private fun Scope.getDataLive(): MutableLiveData<List<RecyclerViewItem>> =
  get(named(NAMED_CONTENT_LIVE))

private fun Scope.getIsLoadingLive(): MutableLiveData<Boolean> =
  get(named(NAMED_IS_LOADING_LIVE))

private fun Scope.getEffectEvent(): LiveEvent<ItemInfoEffect> =
  get(named(NAMED_EFFECT_EVENT))
