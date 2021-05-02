@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.extension.loadBindingModule
import ru.frogogo.whitelabel.extension.scopedQualifier
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoFragment
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoViewModel
import ru.frogogo.whitelabel.ui.coupon_info.delegates.CouponInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.coupon_info.delegates.CouponInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.coupon_info.delegates.CouponInfoContentHandlerDelegate

private const val NAMED_COUPON = "coupon"
private const val NAMED_CONTENT_LIVE = "content_live"

fun Module.couponInfo() {
  scope<CouponInfoFragment> {
    // Core
    viewModel { params ->
      loadBindingModule {
        single(scopedQualifier(NAMED_COUPON)) { params.get<CouponUiModel>() }
      }

      CouponInfoViewModel(get(), get())
    }

    // LiveData
    scoped(named(NAMED_CONTENT_LIVE)) { MutableLiveData<CouponUiModel>() }
    scoped {
      CouponInfoViewModel.LiveDataHolder(
        mutableContentLive = get(named(NAMED_CONTENT_LIVE))
      )
    }

    // Delegates
    scoped {
      CouponInfoContentHandlerDelegate(
        dispatchersProvider = get(),
        coupon = get(scopedQualifier(NAMED_COUPON)),
        mutableContentLive = get(named(NAMED_CONTENT_LIVE)),
      )
    }
    scoped {
      CouponInfoClicksHandlerDelegateImpl(get())
    } bind CouponInfoClicksHandlerDelegate::class
    scoped { CouponInfoViewModel.DelegatesHolder(get(), get()) }
  }
}
