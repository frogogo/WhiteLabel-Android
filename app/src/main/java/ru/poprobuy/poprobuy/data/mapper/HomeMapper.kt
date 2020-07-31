package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState

fun HomeResponse.toUiModel(): HomeState = when (receipt) {
  null -> HomeState.Empty
  else -> HomeState.Receipt(receipt.toUiModel())
}
