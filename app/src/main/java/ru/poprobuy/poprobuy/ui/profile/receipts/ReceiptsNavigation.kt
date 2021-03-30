package ru.poprobuy.poprobuy.ui.profile.receipts

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.extension.toCommand
import ru.poprobuy.poprobuy.ui.profile.receipts.details.ReceiptDetailsButtonState

interface ReceiptsNavigation {
  fun navigateToReceipt(
    receipt: ReceiptUiModel,
    buttonState: ReceiptDetailsButtonState,
  ): NavigationCommand
}

class ReceiptsNavigationImpl : ReceiptsNavigation {

  override fun navigateToReceipt(
    receipt: ReceiptUiModel,
    buttonState: ReceiptDetailsButtonState,
  ): NavigationCommand = ReceiptsFragmentDirections.actionReceiptsToReceiptDetails(
    receipt = receipt,
    buttonState = buttonState
  ).toCommand()
}
