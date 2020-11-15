package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachine
import ru.poprobuy.poprobuy.data.model.api.machine.VendingProduct
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel

fun VendingMachine.toDomain(): VendingMachineUiModel = VendingMachineUiModel(
  products = vendingCells.mapNotNull { it.item?.toDomain() }
)

fun VendingProduct.toDomain(): VendingProductUiModel = VendingProductUiModel(
  id = id,
  name = name,
  imageUrl = imageUrl,
  availableToTake = availableToTake
)
