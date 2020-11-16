package ru.poprobuy.poprobuy.ui.profile.receipts

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.ui.profile.receipts.details.ReceiptDetailsButtonState

interface ReceiptsNavigation {
  fun navigateToReceipt(
    receipt: ReceiptUiModel,
    buttonState: ReceiptDetailsButtonState,
  ): NavigationCommand
}

class ReceiptsNavigationImpl : ReceiptsNavigation {

  override fun navigateToReceipt(receipt: ReceiptUiModel, buttonState: ReceiptDetailsButtonState): NavigationCommand {
    val action = ReceiptsFragmentDirections.actionReceiptsToReceiptDetails(
      receipt = receipt,
      buttonState = buttonState
    )
    return NavigationCommand.ByAction(action)
  }

}
