package ru.frogogo.whitelabel.data.model.ui.home

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import ru.frogogo.whitelabel.data.model.ui.PhotoUiModel
import java.math.BigDecimal

@Keep
@Parcelize
data class HomePromotionUiModel(
  val name: String,
  val photo: PhotoUiModel,
  val steps: List<String>,
  val price: BigDecimal,
  val priceDiscounted: BigDecimal,
) : Parcelable
