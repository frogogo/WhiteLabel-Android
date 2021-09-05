package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeProgress
import ru.frogogo.whitelabel.data.model.api.home.HomePromotion
import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.dictionary.ReceiptState

fun HomeResponse.toDomain(): HomeState =
  if (receipts.isEmpty()) {
    toEmptyState()
  } else {
    toContentState()
  }

fun HomeResponse.toEmptyState(): HomeState =
  HomeState.Empty(
    promotion = promotion.toDomain(),
    items = mutableListOf(),
  )

fun HomeResponse.toContentState(): HomeState {
  val receipts = receipts.toDomain()

  return HomeState.Progress(
    promotion = promotion.toDomain(),
    progress = progress!!.toDomain(),
    coupons = coupons.toDomain(couponData),
    receipts = receipts,
    scanAvailable = receipts.firstOrNull()?.state != ReceiptState.PROCESSING,
  )
}

fun HomePromotion.toDomain(): HomePromotionUiModel =
  HomePromotionUiModel(
    name = name,
    photo = photo.toDomain(),
    steps = steps,
    price = price,
    priceDiscounted = discountedPrice,
  )

fun HomeProgress.toDomain(): HomeProgressUiModel =
  HomeProgressUiModel(
    current = current,
    target = target,
  )
