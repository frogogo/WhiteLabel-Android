package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.ui.profile.coupons.*
import ru.frogogo.whitelabel.ui.profile.coupons.data.CouponsDataFactory
import ru.frogogo.whitelabel.ui.profile.coupons.data.CouponsDataFactoryImpl
import ru.frogogo.whitelabel.ui.profile.coupons.delegate.*

private const val NAMED_DATA_LIVE = "data_live"
private const val NAMED_IS_LOADING_LIVE = "is_loading_live"
private const val NAMED_EFFECT_LIVE_EVENT = "effect_live_event"

@Suppress("USELESS_CAST")
fun Module.coupons() {
  scope<CouponsFragment> {
    viewModel { CouponsViewModel(get(), get()) }
    scoped { CouponsNavigationImpl() as CouponsNavigation }

    // LiveData
    scoped(named(NAMED_DATA_LIVE)) { MutableLiveData<List<RecyclerViewItem>>() }
    scoped(named(NAMED_IS_LOADING_LIVE)) { MutableLiveData<Boolean>() }
    scoped(named(NAMED_EFFECT_LIVE_EVENT)) { LiveEvent<CouponsEffect>() }
    scoped {
      CouponsViewModel.LiveDataHolder(
        mutableDataLive = getDataLive(),
        mutableIsLoadingLive = getIsLoadingLive(),
        mutableEffectLiveEvent = getEffectEventLive(),
      )
    }

    // Delegates
    scoped {
      CouponsDataLoadDelegateImpl(get(), get())
    } bind CouponsDataLoadDelegate::class
    scoped {
      CouponsClicksHandlerDelegateImpl(get(), get())
    } bind CouponsClicksHandlerDelegate::class
    scoped {
      CouponsStateHandlerDelegate(
        dispatchersProvider = get(),
        mutableDataLive = getDataLive(),
        mutableIsLoadingLive = getIsLoadingLive(),
        mutableEffectLiveEvent = getEffectEventLive(),
        dataFactory = get()
      )
    }
    scoped { CouponsViewModel.DelegatesHolder(get(), get(), get()) }

    // Data
    scoped { CouponsDataFactoryImpl() as CouponsDataFactory }
  }
}

private fun Scope.getDataLive(): MutableLiveData<List<RecyclerViewItem>> =
  get(named(NAMED_DATA_LIVE))

private fun Scope.getIsLoadingLive(): MutableLiveData<Boolean> =
  get(named(NAMED_IS_LOADING_LIVE))

private fun Scope.getEffectEventLive(): LiveEvent<CouponsEffect> =
  get(named(NAMED_EFFECT_LIVE_EVENT))
