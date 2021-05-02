package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponImageUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeCouponProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState

fun HomeResponse.toDomain(): HomeState =
  HomeState.Progress(
    couponProgress = HomeCouponProgressUiModel(
      progressCurrent = 25,
      progressTarget = 500,
      notice = "1231 123"
    ),
    coupons = listOf(
      CouponUiModel(
        id = 0,
        name = "Ножеточка Lion Sabatier",
        description = "",
        image = CouponImageUiModel(
          largeUrl = "",
          thumbUrl = "https://picsum.photos/200"
        ),
        qrString = ""
      )
    ),
  )
