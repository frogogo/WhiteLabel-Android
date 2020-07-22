package ru.poprobuy.poprobuy

import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel

object DataFixtures {

  val user = User(
    firstName = "Alex",
    email = "mail@google.com",
    phoneNumber = "+79172795600"
  )

  val product = ProductUiModel(
    id = 1,
    name = "Name",
    imageUrl = "https://picsum.photos/200",
    inStock = true,
    triedBefore = false
  )

}
