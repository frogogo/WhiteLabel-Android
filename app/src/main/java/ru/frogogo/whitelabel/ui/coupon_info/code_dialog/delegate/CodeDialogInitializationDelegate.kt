package ru.frogogo.whitelabel.ui.coupon_info.code_dialog.delegate

import androidx.lifecycle.MutableLiveData
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CodeDialogInitializationDelegate(
  dispatchersProvider: DispatchersProvider,
  private val code: CouponCodeUiModel,
  private val mutableCodeLive: MutableLiveData<CouponCodeUiModel>,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun postData() {
    mutableCodeLive.value = code
  }
}
