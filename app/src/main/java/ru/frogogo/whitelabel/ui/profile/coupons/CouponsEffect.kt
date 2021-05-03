package ru.frogogo.whitelabel.ui.profile.coupons

import ru.frogogo.whitelabel.ui.home.HomeEffect

sealed class CouponsEffect {

  object ShowLoadingError : CouponsEffect()
}
