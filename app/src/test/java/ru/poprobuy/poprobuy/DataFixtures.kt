package ru.poprobuy.poprobuy

import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.model.api.machine.VendingCell
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachine
import ru.poprobuy.poprobuy.data.model.api.machine.VendingProduct
import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

object DataFixtures {

  const val PHONE_NUMBER = "+79172795600"
  const val USER_EMAIL = "mail@google.com"
  const val USER_NAME = "Alex"
  const val SMS_CODE = "3030"
  const val ACCESS_TOKEN = "access_token"
  const val REFRESH_TOKEN = "refresh_token"

  val user = User(
    firstName = USER_NAME,
    email = USER_EMAIL,
    phoneNumber = PHONE_NUMBER
  )

  val product = ProductUiModel(
    id = 1,
    name = "Name",
    imageUrl = "https://picsum.photos/200",
    inStock = true,
    triedBefore = false
  )

  val authenticationResponse = AuthenticationResponse(
    user = user,
    accessToken = ACCESS_TOKEN,
    refreshToken = REFRESH_TOKEN,
    isNew = true
  )

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

  fun getReceiptUIModel(id: Int = 1): ReceiptUiModel = getReceipt(id).toDomain()

  fun getVendingMachine(): VendingMachine = VendingMachine(
    address = "",
    vendingCells = listOf(
      VendingCell(1, 1, getVendingProduct(1), 10),
      VendingCell(2, 2, getVendingProduct(2), 10),
      VendingCell(3, 3, getVendingProduct(3), 10),
      VendingCell(4, 4, getVendingProduct(4), 10),
    ),
    vendingCellsColumns = 10,
    vendingCellsRows = 6
  )

  fun getVendingMachineUiModel(): VendingMachineUiModel = getVendingMachine().toDomain()

  fun getVendingProduct(id: Int = 1): VendingProduct = VendingProduct(
    id = id,
    name = "Name $id",
    imageUrl = "https://picsum.photos/${500 + id}/${500 + id}",
    availableToTake = false
  )

  fun getVendingProductUiModel(id: Int = 1): VendingProductUiModel = getVendingProduct(id).toDomain()

}
