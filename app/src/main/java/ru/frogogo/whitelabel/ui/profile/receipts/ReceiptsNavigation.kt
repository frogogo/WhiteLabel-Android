package ru.frogogo.whitelabel.ui.profile.receipts

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.extension.toCommand
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsButtonState

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
