@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.extension.loadBindingModule
import ru.frogogo.whitelabel.extension.scopedQualifier
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoEffect
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoFragment
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoViewModel
import ru.frogogo.whitelabel.ui.coupon_info.delegate.CouponInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.coupon_info.delegate.CouponInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.coupon_info.delegate.CouponInfoContentHandlerDelegate

private const val NAMED_COUPON = "coupon"
private const val NAMED_CONTENT_LIVE = "content_live"
private const val NAMED_EFFECT_EVENT = "effect_event"

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
    scoped(named(NAMED_EFFECT_EVENT)) { LiveEvent<CouponInfoEffect>() }
    scoped {
      CouponInfoViewModel.LiveDataHolder(
        mutableContentLive = getDataLive(),
        mutableEffectLiveEvent = getEffectEvent(),
      )
    }

    // Delegates
    scoped {
      CouponInfoContentHandlerDelegate(
        dispatchersProvider = get(),
        coupon = getPromotion(),
        mutableContentLive = getDataLive(),
      )
    }
    scoped {
      CouponInfoClicksHandlerDelegateImpl(
        dispatchersProvider = get(),
        coupon = getPromotion(),
        mutableEffectLiveEvent = getEffectEvent(),
      )
    } bind CouponInfoClicksHandlerDelegate::class
    scoped { CouponInfoViewModel.DelegatesHolder(get(), get()) }
  }
}

private fun Scope.getPromotion(): CouponUiModel =
  get(scopedQualifier(NAMED_COUPON))

private fun Scope.getEffectEvent(): LiveEvent<CouponInfoEffect> =
  get(named(NAMED_EFFECT_EVENT))

private fun Scope.getDataLive(): MutableLiveData<CouponUiModel> =
  get(named(NAMED_CONTENT_LIVE))
