package ru.frogogo.whitelabel.ui.scanner.success_dialog

import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseDialogFragment
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.DialogScannerSuccessBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.unloadBindingModuleOnClose
import ru.frogogo.whitelabel.view.dialog.DialogCompanion

private typealias Binding = DialogScannerSuccessBinding

class SuccessScanDialog : BaseDialogFragment(
  layoutId = R.layout.dialog_scanner_success,
  showBackground = false,
), AndroidScopeComponent {

  override val scope: Scope by fragmentScope()

  private val viewModel: SuccessScanViewModel by viewModel {
    parametersOf(requireArguments().getParcelable<ReceiptUiModel>(ARG_RECEIPT))
  }
  private val binding: Binding by viewBinding(Binding::bind)

  override fun initViews() {
    super.initViews()
    binding.buttonClose.setOnSafeClickListener(viewModel::onNavigateBackClicked)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    scope.unloadBindingModuleOnClose()
    viewModel.onCreate()
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    setFragmentResult(KEY_RESULT_DISMISS, Bundle())
  }

  override fun initObservers() {
    super.initObservers()
    viewModel.apply {
      observe(receiptLive, ::renderReceipt)
      observe(effectEvent, ::handleEffect)
    }
  }

  private fun renderReceipt(receipt: ReceiptUiModel) {
    binding.receiptView.bind(receipt)
  }

  private fun handleEffect(effect: SuccessScanEffect) {
    @Exhaustive
    when (effect) {
      SuccessScanEffect.CloseDialog -> dismiss()
    }
  }

  companion object : DialogCompanion<SuccessScanDialog> {

    private const val TAG = "ScannerSuccessDialog"
    private const val ARG_RECEIPT = "arg:receipt"
    private const val KEY_RESULT_DISMISS = "key:dismiss"

    override fun SuccessScanDialog.showIn(fragmentManager: FragmentManager) {
      show(fragmentManager, TAG)
    }

    fun newInstance(
      receipt: ReceiptUiModel,
    ): SuccessScanDialog = SuccessScanDialog().apply {
      arguments = bundleOf(
        ARG_RECEIPT to receipt
      )
    }

    fun setDismissListener(fragment: Fragment, onCloseCallback: () -> Unit) {
      fragment.childFragmentManager.setFragmentResultListener(
        KEY_RESULT_DISMISS,
        fragment.viewLifecycleOwner
      ) { _, _ ->
        onCloseCallback()
      }
    }
  }
}
