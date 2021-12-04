package ru.frogogo.whitelabel.view.dialog

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseDialogFragment
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.databinding.DialogCouponReceivedBinding
import ru.frogogo.whitelabel.util.argument

class CouponReceivedDialogFragment : BaseDialogFragment(R.layout.dialog_coupon_received) {

  private val binding: DialogCouponReceivedBinding by viewBinding()
  private val callbackViewModel: CouponReceivedDialogFragmentCallbackViewModel by sharedViewModel()
  private val coupon: CouponUiModel by argument(ARG_COUPON)

  override fun initViews() {
    binding.apply {
      textViewSubtitle.text = getString(R.string.home_coupon_received_text, coupon.name)
      buttonContinue.setOnClickListener {
        callbackViewModel.onShow(coupon)
        dismissAllowingStateLoss()
      }
    }
  }

  companion object : DialogCompanion<CouponReceivedDialogFragment> {

    private const val TAG = "ErrorDialogFragment"
    private const val ARG_COUPON = "arg:coupon"

    override fun CouponReceivedDialogFragment.showIn(fragmentManager: FragmentManager) {
      show(fragmentManager, TAG)
    }

    fun newInstance(
      coupon: CouponUiModel,
    ): CouponReceivedDialogFragment = CouponReceivedDialogFragment().apply {
      arguments = bundleOf(
        ARG_COUPON to coupon,
      )
    }
  }
}
