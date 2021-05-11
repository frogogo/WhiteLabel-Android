package ru.frogogo.whitelabel.ui.coupon_info.code_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseDialogFragment
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.databinding.DialogCouponCodeBinding
import ru.frogogo.whitelabel.dictionary.CouponCodeType
import ru.frogogo.whitelabel.extension.*
import ru.frogogo.whitelabel.view.dialog.DialogCompanion

private typealias Binding = DialogCouponCodeBinding

class CouponCodeDialog : BaseDialogFragment(
  layoutId = R.layout.dialog_coupon_code,
  showBackground = false
), AndroidScopeComponent {

  override val scope: Scope by fragmentScope()

  private val viewModel: CouponCodeViewModel by viewModel {
    parametersOf(requireArguments().getParcelable<CouponCodeUiModel>(ARG_CODE))
  }
  private val binding: Binding by viewBinding(Binding::bind)

  override fun initViews() {
    super.initViews()
    binding.buttonClose.setOnSafeClickListener { dismiss() }
  }

  override fun initObservers() {
    super.initObservers()
    observe(viewModel.codeLive, ::renderQrCode)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)

    dialog.window?.apply {
      addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
      attributes = attributes.apply { screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL }
    }

    return dialog
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    scope.unloadBindingModuleOnClose()
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()
  }

  private fun renderQrCode(code: CouponCodeUiModel) {
    binding.imageViewCode.apply {
      doOnLayout {
        when (code.type) {
          CouponCodeType.QR -> showQrCode(code.value)
          CouponCodeType.CODE_128 -> showBarcode(code.value)
        }
      }
    }
    binding.textViewCode.apply {
      setVisible(code.type == CouponCodeType.CODE_128)
      text = code.value
    }
  }

  companion object : DialogCompanion<CouponCodeDialog> {

    private const val TAG = "CouponCodeDialog"
    private const val ARG_CODE = "arg:code"

    override fun CouponCodeDialog.showIn(fragmentManager: FragmentManager) {
      show(fragmentManager, TAG)
    }

    fun newInstance(
      code: CouponCodeUiModel,
    ): CouponCodeDialog = CouponCodeDialog().apply {
      arguments = bundleOf(
        ARG_CODE to code
      )
    }
  }
}
