package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponImageUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeCouponProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.dictionary.ReceiptState
import java.util.*

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
      ),
      CouponUiModel(
        id = 1,
        name = "Ножеточка Lion Sabatier",
        description = "",
        image = CouponImageUiModel(
          largeUrl = "",
          thumbUrl = "https://picsum.photos/200"
        ),
        qrString = ""
      )
    ),
    receipts = listOf(
      ReceiptUiModel(
        id = 1,
        number = 0,
        date = Date(),
        value = 0,
        state = ReceiptState.APPROVED,
        rejectReason = null,
      ),
      ReceiptUiModel(
        id = 1,
        number = 0,
        date = Date(),
        value = 0,
        state = ReceiptState.PROCESSING,
        rejectReason = null,
      ),
      ReceiptUiModel(
        id = 1,
        number = 0,
        date = Date(),
        value = 0,
        state = ReceiptState.REJECTED,
        rejectReason = null,
      ),
    )
  )
