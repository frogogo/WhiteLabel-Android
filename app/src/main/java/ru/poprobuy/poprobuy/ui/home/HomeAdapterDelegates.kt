package ru.poprobuy.poprobuy.ui.home

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.databinding.ItemHomeEmptyBinding
import ru.poprobuy.poprobuy.databinding.ItemHomeReceiptBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

object HomeAdapterDelegates {

  fun emptyStateDelegate(
    scanReceiptAction: () -> Unit,
  ) = adapterDelegateViewBinding<HomeState.Empty, RecyclerViewItem, ItemHomeEmptyBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeEmptyBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.buttonScan.setOnSafeClickListener { scanReceiptAction() }
  }

  fun approvedStateDelegate(
    scanMachineCallback: () -> Unit,
    enterMachineAction: () -> Unit,
    scanReceiptAction: () -> Unit,
  ) = adapterDelegateViewBinding<HomeState.Receipt, RecyclerViewItem, ItemHomeReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeReceiptBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.apply {
      layoutControlsGoods.apply {
        buttonScanMachine.setOnSafeClickListener { scanMachineCallback() }
        buttonEnterMachine.setOnSafeClickListener { enterMachineAction() }
      }
      layoutControlsScan.root.setOnSafeClickListener { scanReceiptAction() }
    }

    bind {
      binding.apply {
        viewReceipt.setReceipt(item.receipt)

        layoutControlsGoods.root.setVisible(item.receipt.state == ReceiptState.APPROVED)
        layoutControlsScan.root.setVisible(item.receipt.state == ReceiptState.REJECTED)
      }
    }
  }

}
