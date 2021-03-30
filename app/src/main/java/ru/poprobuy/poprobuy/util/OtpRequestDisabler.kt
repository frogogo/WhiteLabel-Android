package ru.poprobuy.poprobuy.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.util.*
import java.util.concurrent.TimeUnit

class OtpRequestDisabler {

  private var resendTime = Date()

  val disabledForSecondsFlow: Flow<Int?> = flow {
    while (true) {
      emit(getSecondsUntilAvailable())
      delay(250)
    }
  }.distinctUntilChanged().conflate()

  fun submitDelay(delaySeconds: Int) {
    val delayMillis = TimeUnit.SECONDS.toMillis(delaySeconds.toLong())
    resendTime = Date(System.currentTimeMillis() + delayMillis)
  }

  fun getSecondsUntilAvailable(): Int? {
    val secondsToWait = DateUtils.calculateSecondsDifferenceBetweenDates(Date(), resendTime)
    return if (secondsToWait > 0) secondsToWait else null
  }
}
