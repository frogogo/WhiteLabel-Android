package ru.poprobuy.poprobuy.ui.home

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.core.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.databinding.ItemHomeEmptyBinding
import ru.poprobuy.poprobuy.databinding.ItemHomeReceiptBinding
import ru.poprobuy.poprobuy.extension.binding.bind
import ru.poprobuy.poprobuy.extension.binding.initListeners
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener

object HomeAdapterDelegates {

  fun emptyStateDelegate(
    scanReceiptAction: () -> Unit,
  ) = adapterDelegateViewBinding<HomeState.Empty, RecyclerViewItem, ItemHomeEmptyBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeEmptyBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.buttonScan.setOnSafeClickListener { scanReceiptAction() }
  }

  fun approvedStateDelegate(
    scanMachineCallback: (Int) -> Unit,
    enterMachineAction: (Int) -> Unit,
    scanReceiptAction: () -> Unit,
  ) = adapterDelegateViewBinding<HomeState.Receipt, RecyclerViewItem, ItemHomeReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemHomeReceiptBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.apply {
      layoutControlsGoods.initListeners(
        scanMachineClickAction = { scanMachineCallback(item.receipt.id) },
        enterMachineClickAction = { enterMachineAction(item.receipt.id) }
      )
      layoutControlsScan.initListeners { scanReceiptAction() }
    }

    bind {
      binding.apply {
        viewReceipt.bind(item.receipt)

        layoutControlsGoods.bind(item.receipt)
        layoutControlsScan.bind(item.receipt)
      }
    }
  }
}
