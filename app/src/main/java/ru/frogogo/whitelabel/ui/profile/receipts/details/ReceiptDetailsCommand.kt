package ru.frogogo.whitelabel.ui.profile.receipts.details

import androidx.annotation.StringRes

sealed class ReceiptDetailsCommand {
  data class ShowToast(@StringRes val textRes: Int) : ReceiptDetailsCommand()
}
