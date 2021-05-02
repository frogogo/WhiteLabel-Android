package ru.frogogo.whitelabel.data.model.ui.home

sealed class HomeState {

  object Empty : HomeState()

  data class Progress(
    val couponProgress: HomeCouponProgressUiModel,
  ) : HomeState()
}
