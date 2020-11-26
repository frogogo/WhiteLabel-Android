package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.machine.VendingCell
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachine
import ru.poprobuy.poprobuy.data.model.api.machine.VendingProduct
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingCellUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel

fun VendingMachine.toDomain(): VendingMachineUiModel = VendingMachineUiModel(
  id = id,
  cells = vendingCells.mapNotNull { it.toDomain() }
)

fun VendingCell.toDomain(): VendingCellUiModel? {
  return VendingCellUiModel(
    id = id,
    product = item?.toDomain() ?: return null
  )
}

fun VendingProduct.toDomain(): VendingProductUiModel = VendingProductUiModel(
  id = id,
  name = name,
  imageUrl = imageUrl,
  state = state
)
