package ru.frogogo.whitelabel.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState

class HomeMapperKtTest {

  @Test
  fun `empty home maps to empty home state`() {
    val home = HomeResponse(null)

    home.toDomain() shouldBeEqualTo HomeState.Empty
  }

  @Test
  fun `home with receipt maps to receipt home state`() {
    val receipt = DataFixtures.getReceipt()
    val home = HomeResponse(receipt)

    home.toDomain() shouldBeEqualTo HomeState.Receipt(receipt.toDomain())
  }
}
