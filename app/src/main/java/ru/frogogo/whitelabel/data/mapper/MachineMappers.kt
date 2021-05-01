package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.machine.VendingCell
import ru.frogogo.whitelabel.data.model.api.machine.VendingMachine
import ru.frogogo.whitelabel.data.model.api.machine.VendingProduct
import ru.frogogo.whitelabel.data.model.ui.machine.VendingCellUiModel
import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel
import ru.frogogo.whitelabel.data.model.ui.machine.VendingProductUiModel

fun VendingMachine.toDomain(): VendingMachineUiModel = VendingMachineUiModel(
  id = id,
  cells = vendingCells.mapNotNull { it.toDomain() },
  assignExpiresIn = assignExpiresIn
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
