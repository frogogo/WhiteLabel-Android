package ru.frogogo.whitelabel.data.model.api.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.data.model.api.coupon.Coupon
import ru.frogogo.whitelabel.data.model.api.coupon.CouponData
import ru.frogogo.whitelabel.data.model.api.receipt.Receipt

@JsonClass(generateAdapter = true)
data class HomeResponse(
  @Json(name = "promotion")
  val promotion: HomePromotion,
  @Json(name = "coupon")
  val couponData: CouponData,
  @Json(name = "progress")
  val progress: HomeProgress?,
  @Json(name = "receipts")
  val receipts: List<Receipt>,
  @Json(name = "coupons")
  val coupons: List<Coupon>,
)
