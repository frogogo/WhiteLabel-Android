package ru.poprobuy.poprobuy.ui.profile.receipts.details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseBottomSheetDialogFragment
import ru.poprobuy.poprobuy.databinding.DialogReceiptDetailsBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.extension.binding.setReceipt
import ru.poprobuy.poprobuy.extension.binding.useLargeSize
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

class ReceiptDetailsBottomDialog : BaseBottomSheetDialogFragment<ReceiptDetailsViewModel>(
  R.layout.dialog_receipt_details
) {

  override val viewModel: ReceiptDetailsViewModel by viewModel()

  private val args: ReceiptDetailsBottomDialogArgs by navArgs()
  private val binding: DialogReceiptDetailsBinding by viewBinding()

  override fun initViews() {
    binding.apply {
      layoutHeader.setReceipt(args.receipt)
      layoutHeader.useLargeSize()
    }
    initFooters()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    dialog.setOnShowListener { dialogInterface ->
      val bottomDialog = dialogInterface as BottomSheetDialog
      val bottomSheet = bottomDialog.findViewById<View>(DESIGN_BOTTOM_SHEET_ID) as FrameLayout
      BottomSheetBehavior.from(bottomSheet).apply {
        state = BottomSheetBehavior.STATE_EXPANDED
        peekHeight = binding.root.height
      }
    }

    return dialog
  }

  private fun initFooters() {
    val receipt = args.receipt

    binding.apply {
      // Footer
      layoutFooterApproved.setReceipt(receipt)
      layoutFooterProcessing.setReceipt(receipt)
      layoutFooterCompleted.setReceipt(receipt)
      layoutFooterRejected.setReceipt(receipt, showTopDivider = true)
      // Controls
      layoutControlsGoods.apply {
        root.setVisible(receipt.state == ReceiptState.APPROVED)
        buttonEnterMachine.setOnSafeClickListener { viewModel.navigateToMachineEnter(receipt.id) }
        buttonScanMachine.setOnSafeClickListener { viewModel.navigateToMachineScan(receipt.id) }
      }
      layoutControlsScan.root.apply {
        setVisible(receipt.state in listOf(ReceiptState.COMPLETED, ReceiptState.REJECTED))
        setOnSafeClickListener(viewModel::navigateToReceiptScan)
      }
    }
  }

  companion object {
    private val DESIGN_BOTTOM_SHEET_ID = com.google.android.material.R.id.design_bottom_sheet
  }

}
