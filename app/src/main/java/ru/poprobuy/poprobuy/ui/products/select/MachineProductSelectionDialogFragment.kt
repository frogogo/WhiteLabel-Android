package ru.poprobuy.poprobuy.ui.products.select

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.github.ajalt.timberkt.d
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseDialogFragment
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingCellUiModel
import ru.poprobuy.poprobuy.databinding.DialogMachineProductSelectionBinding
import ru.poprobuy.poprobuy.extension.observe
import ru.poprobuy.poprobuy.extension.observeEvent
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.util.argument
import ru.poprobuy.poprobuy.view.dialog.DialogCompanion

class MachineProductSelectionDialogFragment : BaseDialogFragment(R.layout.dialog_machine_product_selection),
  DialogInterface.OnKeyListener {

  private val viewModel: ProductSelectionViewModel by viewModel {
    parametersOf(
      ProductSelectionViewModel.Params(
        receiptId = receiptId,
        vendingMachineId = vendingMachineId,
        vendingCell = vendingCell
      )
    )
  }
  private val binding: DialogMachineProductSelectionBinding by viewBinding {
    DialogMachineProductSelectionBinding.bind(requireView())
  }

  private val receiptId: Int by argument(ARG_RECEIPT_ID)
  private val vendingMachineId: Int by argument(ARG_VENDING_MACHINE_ID)
  private val vendingCell: VendingCellUiModel by argument(ARG_VENDING_CELL)

  override fun initViews() {
    binding.apply {
      layoutProduct.apply {
        buttonYes.setOnSafeClickListener { viewModel.takeProduct() }
        buttonNo.setOnSafeClickListener { viewModel.requestDismiss() }
      }
      layoutSuccess.buttonDone.setOnSafeClickListener { viewModel.requestDismiss() }
    }

    requireDialog().setOnKeyListener(this)
  }

  override fun initObservers() {
    viewModel.apply {
      observe(stateLive, ::renderState)
      observeEvent(commandLive, ::handleCommand)
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
    object : Dialog(requireContext(), theme) {
      /**
       * Intercepts outside click events to prevent dialog canceling
       * and give an ability to handle such events
       */
      override fun onTouchEvent(event: MotionEvent): Boolean {
        // Handles down event
        if (event.action == MotionEvent.ACTION_DOWN) {
          // Get view rect
          val rect = Rect().apply { requireView().getGlobalVisibleRect(this) }
          // Passes event to view model only if event was occurred
          // outside of dialog view and dialog is cancelable
          if (!rect.contains(event.x.toInt(), event.y.toInt()) && isCancelable) {
            viewModel.requestDismiss()
            return true
          }
        }
        return false
      }
    }

  override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
    // KEYCODE_BACK event should be handled regardless if isCancelable dialog state
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      d { "Back button clicked" }
      if (isCancelable) {
        viewModel.requestDismiss()
      }
      return true
    }
    return false
  }

  private fun renderState(state: MachineProductSelectionState) {
    binding.apply {
      layoutProduct.root.setVisible(state is MachineProductSelectionState.Product)
      layoutSuccess.root.setVisible(state is MachineProductSelectionState.Success)
    }

    if (state is MachineProductSelectionState.Product) {
      binding.layoutProduct.apply {
        imageViewIcon.load(state.product.imageUrl) {
          placeholder(R.drawable.ic_placeholder)
        }

        textViewTitle.text = if (!state.isLoading) {
          getString(R.string.product_select_title_confirmation, state.product.name)
        } else {
          getString(R.string.product_select_title_goods_issue)
        }
        buttonYes.setVisible(!state.isLoading)
        buttonNo.setVisible(!state.isLoading)
        progressBar.setVisible(state.isLoading)
      }
    }
  }

  private fun handleCommand(command: MachineProductSelectionCommand) {
    @Exhaustive
    when (command) {
      is MachineProductSelectionCommand.SetCancelable -> isCancelable = command.isCancelable
      MachineProductSelectionCommand.DismissDialog -> dismiss()
    }
  }

  companion object : DialogCompanion<MachineProductSelectionDialogFragment> {

    private const val TAG = "ProductSelectionDialogFragment"

    private const val ARG_RECEIPT_ID = "arg:receipt_id"
    private const val ARG_VENDING_MACHINE_ID = "arg:vending_machine_id"
    private const val ARG_VENDING_CELL = "arg:cell"

    override fun MachineProductSelectionDialogFragment.showIn(fragmentManager: FragmentManager) {
      show(fragmentManager, TAG)
    }

    fun newInstance(
      receiptId: Int,
      vendingMachineId: Int,
      vendingCell: VendingCellUiModel,
    ): MachineProductSelectionDialogFragment =
      MachineProductSelectionDialogFragment().apply {
        arguments = bundleOf(
          ARG_RECEIPT_ID to receiptId,
          ARG_VENDING_MACHINE_ID to vendingMachineId,
          ARG_VENDING_CELL to vendingCell,
        )
      }
  }

}
