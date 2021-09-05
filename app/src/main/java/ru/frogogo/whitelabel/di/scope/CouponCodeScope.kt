package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.extension.loadBindingModule
import ru.frogogo.whitelabel.extension.scopedQualifier
import ru.frogogo.whitelabel.ui.coupon_info.code_dialog.CouponCodeDialog
import ru.frogogo.whitelabel.ui.coupon_info.code_dialog.CouponCodeViewModel
import ru.frogogo.whitelabel.ui.coupon_info.code_dialog.delegate.CodeDialogInitializationDelegate

private const val NAMED_CODE = "code"
private const val NAMED_CODE_LIVE = "code_live"

fun Module.couponCode() {
  scope<CouponCodeDialog> {
    // Code
    viewModel { params ->
      loadBindingModule {
        single(scopedQualifier(NAMED_CODE)) { params.get<CouponCodeUiModel>() }
      }
      CouponCodeViewModel(get(), get())
    }

    // LiveData
    scoped(named(NAMED_CODE_LIVE)) { MutableLiveData<CouponCodeUiModel>() }
    scoped {
      CouponCodeViewModel.LiveDataHolder(
        mutableCodeLive = getCodeLive(),
      )
    }

    // Delegates
    scoped {
      CodeDialogInitializationDelegate(
        dispatchersProvider = get(),
        code = get(scopedQualifier(NAMED_CODE)),
        mutableCodeLive = getCodeLive(),
      )
    }
    scoped { CouponCodeViewModel.DelegatesHolder(get()) }
  }
}

private fun Scope.getCodeLive(): MutableLiveData<CouponCodeUiModel> =
  get(named(NAMED_CODE_LIVE))
