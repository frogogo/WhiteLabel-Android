package ru.poprobuy.poprobuy.data.model.ui.product

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures

class ProductUiModelTest {

  @Test
  fun `product is available when not tried and is in stock`() {
    DataFixtures.product
      .isActive().shouldBeTrue()
  }

  @Test
  fun `product is not available when tried and is in stock`() {
    DataFixtures.product.copy(triedBefore = true)
      .isActive().shouldBeFalse()
  }

  @Test
  fun `product is not available when not tried and not in stock`() {
    DataFixtures.product.copy(inStock = false)
      .isActive().shouldBeFalse()
  }

  @Test
  fun `product is not available when tried and not in stock`() {
    DataFixtures.product.copy(triedBefore = true, inStock = false)
      .isActive().shouldBeFalse()
  }

}
