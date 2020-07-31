package ru.poprobuy.poprobuy

import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

object DataFixtures {

  const val PHONE_NUMBER = "+79172795600"
  const val USER_EMAIL = "mail@google.com"
  const val USER_NAME = "Alex"
  const val SMS_CODE = "3030"

  val user = User(
    firstName = USER_NAME,
    email = USER_EMAIL,
    phoneNumber = PHONE_NUMBER
  )

  val receipt = Receipt(
    id = 1,
    number = 101,
    state = ReceiptState.PROCESSING,
    timestamp = Date(),
    sum = 100
  )

  val product = ProductUiModel(
    id = 1,
    name = "Name",
    imageUrl = "https://picsum.photos/200",
    inStock = true,
    triedBefore = false
  )

  val authenticationResponse = AuthenticationResponse(
    accessToken = "token",
    isNew = true
  )

  val home = HomeResponse(receipt)

}
