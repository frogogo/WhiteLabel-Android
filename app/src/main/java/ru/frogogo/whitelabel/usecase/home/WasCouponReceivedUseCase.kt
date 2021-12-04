package ru.frogogo.whitelabel.usecase.home

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.repository.UserRepository

class WasCouponReceivedUseCase(
  private val userRepository: UserRepository,
) {

  operator fun invoke(state: HomeState): CouponUiModel? {
    if (state !is HomeState.Progress) {
      return null
    }

    val receivedCouponsRemote = state.coupons.size
    val receivedCouponsLocal = userRepository.getReceivedCouponsCount()
    val newCouponReceived = receivedCouponsLocal < receivedCouponsRemote

    return if (newCouponReceived) {
      userRepository.saveReceivedCouponsCount(receivedCouponsRemote)
      state.coupons.first()
    } else {
      null
    }
  }
}
