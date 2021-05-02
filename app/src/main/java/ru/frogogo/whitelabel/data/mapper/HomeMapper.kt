package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeCouponProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState

fun HomeResponse.toDomain(): HomeState = when (receipt) {
  null -> HomeState.Progress(HomeCouponProgressUiModel(progressCurrent = 25, progressTarget = 500, notice = "1231 123"))
  else -> HomeState.Progress(HomeCouponProgressUiModel(progressCurrent = 0, progressTarget = 500, notice = "1231 123"))
}
