package ru.frogogo.whitelabel.extension.binding

import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ViewReceiptControlsGoodsBinding
import ru.frogogo.whitelabel.databinding.ViewReceiptControlsScanBinding
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.extension.setVisible

fun ViewReceiptControlsGoodsBinding.initListeners(
  scanMachineClickAction: () -> Unit,
  enterMachineClickAction: () -> Unit,
) {
  buttonScanMachine.setSafeOnClickListener { scanMachineClickAction() }
  buttonEnterMachine.setSafeOnClickListener { enterMachineClickAction() }
}

fun ViewReceiptControlsGoodsBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.APPROVED)
}

fun ViewReceiptControlsScanBinding.initListeners(scanClickAction: () -> Unit) {
  buttonScan.setSafeOnClickListener { scanClickAction() }
}

fun ViewReceiptControlsScanBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state in listOf(ReceiptState.COMPLETED, ReceiptState.REJECTED))
}
