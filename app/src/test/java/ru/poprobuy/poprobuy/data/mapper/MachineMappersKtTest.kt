package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.data.model.api.machine.VendingCell
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachine
import ru.poprobuy.poprobuy.data.model.api.machine.VendingProduct
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel

internal class MachineMappersKtTest {

  @Test
  fun `VendingMachine should be mapped to domain model`() {
    val machine = VendingMachine(
      address = "address",
      vendingCells = listOf(
        VendingCell(1, 1, VendingProduct(id = 1, name = "name1", imageUrl = "name2", availableToTake = true), 10),
        VendingCell(2, 2, VendingProduct(id = 2, name = "name2", imageUrl = "image2", availableToTake = false), 10),
        VendingCell(4, 3, null, 10),
      ),
      vendingCellsColumns = 10,
      vendingCellsRows = 6
    )

    val expected = VendingMachineUiModel(
      products = listOf(
        VendingProductUiModel(
          id = 1,
          name = "name1",
          imageUrl = "name2",
          availableToTake = true
        ),
        VendingProductUiModel(
          id = 2,
          name = "name2",
          imageUrl = "image2",
          availableToTake = false
        )
      )
    )

    machine.toDomain() shouldBeEqualTo expected
  }

  @Test
  fun `VendingProduct should be mapped to domain model`() {
    val product = VendingProduct(
      id = 0,
      name = "name",
      imageUrl = "image",
      availableToTake = true
    )

    val expected = VendingProductUiModel(
      id = 0,
      name = "name",
      imageUrl = "image",
      availableToTake = true
    )

    product.toDomain() shouldBeEqualTo expected
  }

}
