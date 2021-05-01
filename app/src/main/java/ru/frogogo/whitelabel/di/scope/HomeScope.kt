@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.ui.home.*
import ru.frogogo.whitelabel.ui.home.delegate.*

private const val NAMED_DATA_LIVE = "data_live"
private const val NAMED_IS_LOADING_LIVE = "is_loading_live"
private const val NAMED_EFFECT_LIVE_EVENT = "effect_live_event"

fun Module.homeScope() {
  scope<HomeFragment> {
    // Core
    viewModel { HomeViewModel(get(), get()) }
    scoped { HomeNavigationImpl() as HomeNavigation }

    // LiveData
    scoped(named(NAMED_DATA_LIVE)) { MutableLiveData<List<RecyclerViewItem>>() }
    scoped(named(NAMED_IS_LOADING_LIVE)) { MutableLiveData<Boolean>() }
    scoped(named(NAMED_EFFECT_LIVE_EVENT)) { LiveEvent<HomeEffect>() }
    scoped {
      HomeViewModel.LiveDataHolder(
        mutableDataLive = get(named(NAMED_DATA_LIVE)),
        mutableIsLoadingLive = get(named(NAMED_IS_LOADING_LIVE)),
        mutableEffectLiveEvent = get(named(NAMED_EFFECT_LIVE_EVENT)),
      )
    }

    // Delegates
    scoped {
      HomeDataLoadDelegateImpl(get(), get(), get())
    } bind HomeDataLoadDelegate::class
    scoped {
      HomeClickHandlerDelegateImpl(get(), get())
    } bind HomeClickHandlerDelegate::class
    scoped {
      HomeStateHandlerDelegate(
        dispatchersProvider = get(),
        mutableDataLive = get(named(NAMED_DATA_LIVE)),
        mutableIsLoadingLive = get(named(NAMED_IS_LOADING_LIVE)),
        effectLiveEvent = get(named(NAMED_EFFECT_LIVE_EVENT)),
      )
    }
    scoped { HomeViewModel.DelegatesHolder(get(), get()) }
  }
}
