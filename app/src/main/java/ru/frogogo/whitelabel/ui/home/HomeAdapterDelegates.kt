package ru.frogogo.whitelabel.ui.home

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.databinding.ItemHomeEmptyBinding
import ru.frogogo.whitelabel.databinding.ItemHomeReceiptBinding
import ru.frogogo.whitelabel.extension.binding.bind
import ru.frogogo.whitelabel.extension.binding.initListeners
import ru.frogogo.whitelabel.extension.setOnSafeClickListener

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
