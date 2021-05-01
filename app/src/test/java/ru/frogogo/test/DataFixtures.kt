package ru.frogogo.test

import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponse
import ru.frogogo.whitelabel.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.api.machine.VendingCell
import ru.frogogo.whitelabel.data.model.api.machine.VendingMachine
import ru.frogogo.whitelabel.data.model.api.machine.VendingProduct
import ru.frogogo.whitelabel.data.model.api.receipt.Receipt
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptDistributionNetwork
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptProduct
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptRejectReason
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.model.ui.machine.VendingCellUiModel
import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel
import ru.frogogo.whitelabel.data.model.ui.machine.VendingProductUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptDistributionNetworkUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptProductUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptRejectReasonUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.dictionary.VendingProductState
import java.util.*

object DataFixtures {

  const val PHONE_NUMBER = "+79172795600"
  const val USER_EMAIL = "mail@google.com"
  const val USER_NAME = "Alex"
  const val SMS_CODE = "3030"
  const val ACCESS_TOKEN = "access_token"
  const val REFRESH_TOKEN = "refresh_token"
  const val SESSION_ID = "session-id"
  const val QR_RECEIPT = "qr_receipt"

  val user = User(
    firstName = USER_NAME,
    email = USER_EMAIL,
    phoneNumber = PHONE_NUMBER
  )

  val authenticationResponse = AuthenticationResponse(
    user = user,
    accessToken = ACCESS_TOKEN,
    refreshToken = REFRESH_TOKEN,
    isNew = true
  )

  val confirmationCodeRequestResponse =
    ConfirmationCodeRequestResponse(60)

  val home = HomeResponse(getReceipt())

  fun getReceipt(id: Int = 1): Receipt = Receipt(
    id = id,
    number = 101,
    sum = 100,
    state = ReceiptState.PROCESSING,
    timestamp = Date(),
    promotion = null,
    product = null,
    rejectReason = null
  )

  fun getReceiptUIModel(id: Int = 1): ReceiptUiModel =
    getReceipt(id).toDomain()

  fun getVendingMachine(id: Int = 1): VendingMachine = VendingMachine(
    id = id,
    vendingCells = listOf(
      VendingCell(1, getVendingProduct(1)),
      VendingCell(2, getVendingProduct(2)),
      VendingCell(3, getVendingProduct(3)),
      VendingCell(4, getVendingProduct(4)),
    ),
    assignExpiresIn = 60
  )

  fun getVendingMachineUiModel(id: Int = 1): VendingMachineUiModel =
    getVendingMachine(id).toDomain()

  fun getVendingCell(id: Int = 1): VendingCell = VendingCell(
    id = id,
    getVendingProduct(id * 10)
  )

  fun getVendingCellUiModel(id: Int = 1): VendingCellUiModel =
    getVendingCell(id).toDomain()!!

  fun getVendingProduct(id: Int = 1): VendingProduct = VendingProduct(
    id = id,
    name = "Name $id",
    imageUrl = "https://picsum.photos/${500 + id}/${500 + id}",
    state = VendingProductState.AVAILABLE
  )

  fun getVendingProductUiModel(id: Int = 1): VendingProductUiModel =
    getVendingProduct(id).toDomain()

  fun getReceiptRejectReason(): ReceiptRejectReason =
    ReceiptRejectReason("reason", "reason_text")

  fun getReceiptRejectReasonUIModel(): ReceiptRejectReasonUiModel =
    getReceiptRejectReason().toDomain()

  fun getReceiptDistributionNetwork(): ReceiptDistributionNetwork =
    ReceiptDistributionNetwork("name")

  fun getReceiptDistributionNetworkUiModel(): ReceiptDistributionNetworkUiModel =
    getReceiptDistributionNetwork().toDomain()

  fun getReceiptProduct(id: Int = 1): ReceiptProduct = ReceiptProduct(
    id = id,
    name = "Name $id",
    imageUrl = "https://picsum.photos/${500 + id}/${500 + id}"
  )

  fun getReceiptProductUiModel(id: Int = 1): ReceiptProductUiModel =
    getReceiptProduct(id).toDomain()
}
