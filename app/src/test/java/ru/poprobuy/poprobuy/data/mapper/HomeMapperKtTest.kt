package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState

class HomeMapperKtTest {

  @Test
  fun `empty home maps to empty home state`() {
    val home = HomeResponse(null)

    home.toUiModel() shouldBeEqualTo HomeState.Empty
  }

  @Test
  fun `home with receipt maps to receipt home state`() {
    val home = HomeResponse(DataFixtures.receipt)

    home.toUiModel() shouldBeEqualTo HomeState.Receipt(DataFixtures.receipt.toUiModel())
  }

}
