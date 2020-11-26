package ru.poprobuy.poprobuy.dictionary

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import ru.poprobuy.poprobuy.R

enum class VendingProductState {
  AVAILABLE,
  ALREADY_RECEIVED,
  OUT_OF_STOCK,
  UNAVAILABLE;

  /**
   * @return color resource for given [VendingProductState]
   */
  @ColorRes
  fun getColorRes(): Int? = when (this) {
    AVAILABLE -> null
    ALREADY_RECEIVED -> R.color.products_state_tried_before
    OUT_OF_STOCK -> R.color.products_state_out_of_stock
    UNAVAILABLE -> R.color.products_state_unavailable
  }

  /**
   * @return string name resource for given [VendingProductState]
   */
  @StringRes
  fun getNameRes(): Int? = when (this) {
    AVAILABLE -> null
    ALREADY_RECEIVED -> R.string.products_status_tried_before
    OUT_OF_STOCK -> R.string.products_status_out_of_stock
    UNAVAILABLE -> R.string.products_status_unavailable
  }

}
