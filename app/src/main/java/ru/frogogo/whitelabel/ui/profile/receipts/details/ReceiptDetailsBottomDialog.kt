package ru.frogogo.whitelabel.ui.profile.receipts.details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseBottomSheetDialogFragment
import ru.frogogo.whitelabel.databinding.DialogReceiptDetailsBinding
import ru.frogogo.whitelabel.extension.binding.bind
import ru.frogogo.whitelabel.extension.binding.initListeners
import ru.frogogo.whitelabel.extension.binding.useLargeSize
import ru.frogogo.whitelabel.extension.observe

class ReceiptDetailsBottomDialog : BaseBottomSheetDialogFragment<ReceiptDetailsViewModel>(
  R.layout.dialog_receipt_details,
) {

  override val viewModel: ReceiptDetailsViewModel by viewModel { parametersOf(args.buttonState) }

  private val args: ReceiptDetailsBottomDialogArgs by navArgs()
  private val binding: DialogReceiptDetailsBinding by viewBinding()

  override fun initViews() {
    binding.apply {
      layoutHeader.bind(args.receipt)
      layoutHeader.useLargeSize()
    }
    initFooters()
  }

  override fun initObservers() {
    observe(viewModel.commandEvent, ::handleCommand)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    dialog.setOnShowListener { dialogInterface ->
      val bottomDialog = dialogInterface as BottomSheetDialog
      val bottomSheet = bottomDialog.findViewById<View>(DESIGN_BOTTOM_SHEET_ID) as FrameLayout
      BottomSheetBehavior.from(bottomSheet).apply {
        state = BottomSheetBehavior.STATE_EXPANDED
        skipCollapsed = true
      }
    }

    return dialog
  }

  private fun initFooters() {
    val receipt = args.receipt

    binding.apply {
      // Footer
      layoutFooterApproved.bind(receipt)
      layoutFooterProcessing.bind(receipt)
      layoutFooterRejected.bind(receipt, showTopDivider = true)

      // Controls
      layoutControlsGoods.initListeners(
        scanMachineClickAction = { /* TODO */ },
        enterMachineClickAction = { /* TODO */ },
      )
      layoutControlsGoods.bind(receipt)

      layoutControlsScan.initListeners { viewModel.navigateToReceiptScan() }
      layoutControlsScan.bind(receipt)
    }
  }

  private fun handleCommand(command: ReceiptDetailsCommand) {
    @Exhaustive
    when (command) {
      is ReceiptDetailsCommand.ShowToast -> Toast.makeText(requireContext(), command.textRes, Toast.LENGTH_LONG).show()
    }
  }

  companion object {
    private val DESIGN_BOTTOM_SHEET_ID = com.google.android.material.R.id.design_bottom_sheet
  }
}
