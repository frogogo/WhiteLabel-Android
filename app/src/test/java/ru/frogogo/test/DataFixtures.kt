package ru.frogogo.test

import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponse
import ru.frogogo.whitelabel.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.api.receipt.Receipt
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptRejectReason
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptRejectReasonUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.dictionary.ReceiptState
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
