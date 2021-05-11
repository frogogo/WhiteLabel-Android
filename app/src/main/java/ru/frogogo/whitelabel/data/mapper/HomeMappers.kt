package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeProgress
import ru.frogogo.whitelabel.data.model.api.home.HomePromotion
import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState

fun HomeResponse.toDomain(): HomeState =
  if (receipts.isEmpty()) {
    toEmptyState()
  } else {
    toContentState()
  }

fun HomeResponse.toEmptyState(): HomeState =
  HomeState.Empty(
    promotion = promotion.toDomain(),
  )

fun HomeResponse.toContentState(): HomeState =
  HomeState.Progress(
    receipts = receipts.toDomain(),
    progress = progress!!.toDomain(),
    coupons = coupons.toDomain(couponData),
  )

fun HomePromotion.toDomain(): HomePromotionUiModel =
  HomePromotionUiModel(
    name = name,
    photoUrl = photoUrl,
    steps = steps,
  )

fun HomeProgress.toDomain(): HomeProgressUiModel =
  HomeProgressUiModel(
    current = current,
    target = target,
  )
