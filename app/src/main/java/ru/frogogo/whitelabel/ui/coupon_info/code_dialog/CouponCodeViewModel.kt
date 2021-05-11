package ru.frogogo.whitelabel.ui.coupon_info.code_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.ui.coupon_info.code_dialog.delegate.CodeDialogInitializationDelegate

class CouponCodeViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel() {

  val codeLive: LiveData<CouponCodeUiModel> = liveData.mutableCodeLive

  override fun onStart() {
    super.onStart()
    delegates.initializationDelegate.postData()
  }

  override fun onCleared() {
    super.onCleared()
    delegates.initializationDelegate.cancelJob()
  }

  class LiveDataHolder(
    val mutableCodeLive: MutableLiveData<CouponCodeUiModel>,
  )

  class DelegatesHolder(
    val initializationDelegate: CodeDialogInitializationDelegate,
  )
}
