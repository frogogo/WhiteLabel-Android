package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.Test
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.poprobuy.data.model.api.machine.VendingCell
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachine
import ru.poprobuy.poprobuy.data.model.api.machine.VendingProduct
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingCellUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel
import ru.poprobuy.poprobuy.dictionary.VendingProductState

internal class MachineMappersKtTest {

  @Test
  fun `VendingMachine should be mapped to domain model`() {
    val cell1 = DataFixtures.getVendingCell(1)
    val cell2 = DataFixtures.getVendingCell(2)
    val cell3 = DataFixtures.getVendingCell(3).copy(item = null)
    val machine = VendingMachine(
      id = 1,
      vendingCells = listOf(
        cell1,
        cell2,
        cell3
      ),
      assignExpiresIn = 60
    )

    val expected = VendingMachineUiModel(
      id = 1,
      cells = listOf(
        cell1.toDomain()!!,
        cell2.toDomain()!!
      ),
      assignExpiresIn = 60
    )

    machine.toDomain() shouldBeEqualTo expected
  }

  @Test
  fun `VendingCell should be mapped to domain model`() {
    val product = DataFixtures.getVendingProduct(1)
    val cell = VendingCell(
      id = 1,
      item = product
    )

    val expected = VendingCellUiModel(
      id = 1,
      product = product.toDomain()
    )

    cell.toDomain() shouldBeEqualTo expected
  }

  @Test
  fun `VendingCell should be mapped to null if product is null`() {
    val cell = VendingCell(
      id = 1,
      item = null
    )

    cell.toDomain().shouldBeNull()
  }

  @Test
  fun `VendingProduct should be mapped to domain model`() {
    val product = VendingProduct(
      id = 0,
      name = "name",
      imageUrl = "image",
      state = VendingProductState.UNAVAILABLE
    )

    val expected = VendingProductUiModel(
      id = 0,
      name = "name",
      imageUrl = "image",
      state = VendingProductState.UNAVAILABLE
    )

    product.toDomain() shouldBeEqualTo expected
  }
}
