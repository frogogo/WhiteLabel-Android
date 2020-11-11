package ru.poprobuy.poprobuy.ui.profile.receipts

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel

interface ReceiptsNavigation {
  fun navigateToReceipt(receipt: ReceiptUiModel): NavigationCommand
}

class ReceiptsNavigationImpl : ReceiptsNavigation {

  override fun navigateToReceipt(receipt: ReceiptUiModel): NavigationCommand {
    val action = ReceiptsFragmentDirections.actionReceiptsToReceiptDetails(receipt)
    return NavigationCommand.ByAction(action)
  }

}
