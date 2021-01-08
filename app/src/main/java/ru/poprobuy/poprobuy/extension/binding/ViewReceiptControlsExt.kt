package ru.poprobuy.poprobuy.extension.binding

import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptControlsGoodsBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptControlsScanBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

fun ViewReceiptControlsGoodsBinding.initListeners(
  scanMachineClickAction: () -> Unit,
  enterMachineClickAction: () -> Unit,
) {
  buttonScanMachine.setOnSafeClickListener { scanMachineClickAction() }
  buttonEnterMachine.setOnSafeClickListener { enterMachineClickAction() }
}

fun ViewReceiptControlsGoodsBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.APPROVED)
}

fun ViewReceiptControlsScanBinding.initListeners(scanClickAction: () -> Unit) {
  buttonScan.setOnSafeClickListener { scanClickAction() }
}

fun ViewReceiptControlsScanBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state in listOf(ReceiptState.COMPLETED, ReceiptState.REJECTED))
}
