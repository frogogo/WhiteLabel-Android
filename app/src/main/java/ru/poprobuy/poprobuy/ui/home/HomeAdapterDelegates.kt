package ru.poprobuy.poprobuy.ui.home

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.databinding.ItemHomeEmptyBinding
import ru.poprobuy.poprobuy.databinding.ItemHomeReceiptBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

object HomeAdapterDelegates {

  fun emptyStateDelegate(
    scanReceiptAction: () -> Unit
  ) = adapterDelegateViewBinding<HomeState.Empty, RecyclerViewItem, ItemHomeEmptyBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeEmptyBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.buttonScan.setOnSafeClickListener { scanReceiptAction() }
  }

  fun acceptedStateDelegate(
    scanMachineCallback: () -> Unit,
    enterMachineAction: () -> Unit,
    scanReceiptAction: () -> Unit
  ) = adapterDelegateViewBinding<HomeState.Receipt, RecyclerViewItem, ItemHomeReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeReceiptBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.apply {
      buttonScanMachine.setOnSafeClickListener { scanMachineCallback() }
      buttonEnterMachine.setOnSafeClickListener { enterMachineAction() }
      buttonScanReceipt.setOnSafeClickListener { scanReceiptAction() }
    }

    bind {
      binding.apply {
        viewReceipt.setReceipt(item.receipt)

        layoutAccept.setVisible(item.receipt.status == ReceiptStatus.ACCEPTED)
        buttonScanReceipt.setVisible(item.receipt.status == ReceiptStatus.REJECTED)
      }
    }
  }

}
