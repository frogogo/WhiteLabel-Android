package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState

fun HomeResponse.toDomain(): HomeState = when (receipt) {
  null -> HomeState.Empty
  else -> HomeState.Receipt(receipt.toDomain())
}
