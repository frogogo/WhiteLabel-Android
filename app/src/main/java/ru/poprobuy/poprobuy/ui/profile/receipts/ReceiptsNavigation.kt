package ru.poprobuy.poprobuy.ui.profile.receipts

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel

interface ReceiptsNavigation {
  fun navigateToReceipt(receipt: ReceiptUiModel): NavigationCommand
}

class ReceiptsNavigationImpl : ReceiptsNavigation {

  override fun navigateToReceipt(receipt: ReceiptUiModel): NavigationCommand {
    return NavigationCommand.Back
  }

}
