@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.ui.home.HomeFragment
import ru.frogogo.whitelabel.ui.home.HomeNavigation
import ru.frogogo.whitelabel.ui.home.HomeNavigationImpl
import ru.frogogo.whitelabel.ui.home.HomeViewModel
import ru.frogogo.whitelabel.ui.home.delegate.HomeClickHandlerDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeClickHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.home.delegate.HomeDataLoadDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeDataLoadDelegateImpl

private const val NAMED_DATA_LIVE = "data_live"
private const val NAMED_IS_LOADING_LIVE = "is_loading_live"

fun Module.homeScope() {
  scope<HomeFragment> {
    // Core
    viewModel { HomeViewModel(get(), get()) }
    scoped { HomeNavigationImpl() as HomeNavigation }

    // LiveData
    scoped(named(NAMED_DATA_LIVE)) { MutableLiveData<List<RecyclerViewItem>>() }
    scoped(named(NAMED_IS_LOADING_LIVE)) { MutableLiveData<Boolean>() }
    scoped {
      HomeViewModel.LiveDataHolder(
        mutableDataLive = get(named(NAMED_DATA_LIVE)),
        mutableIsLoadingLive = get(named(NAMED_IS_LOADING_LIVE))
      )
    }

    // Delegates
    scoped {
      HomeDataLoadDelegateImpl(
        dispatchersProvider = get(),
        mutableDataLive = get(named(NAMED_DATA_LIVE)),
        mutableIsLoadingLive = get(named(NAMED_IS_LOADING_LIVE)),
        getHomeUseCase = get()
      )
    } bind HomeDataLoadDelegate::class
    scoped {
      HomeClickHandlerDelegateImpl(
        dispatchersProvider = get(),
        navigation = get()
      )
    } bind HomeClickHandlerDelegate::class
    scoped { HomeViewModel.DelegatesHolder(get(), get()) }
  }
}
