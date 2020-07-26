package ru.poprobuy.poprobuy.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.CoroutinesTestRule

@ExperimentalCoroutinesApi
class OtpRequestDisablerTest {

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()

  private lateinit var otpRequestDisabler: OtpRequestDisabler

  @Before
  fun setUp() {
    otpRequestDisabler = OtpRequestDisabler()
  }

  @Test
  fun `initially always emits 0`() = runBlockingTest {
    otpRequestDisabler.disabledForSecondsFlow.take(1).collect {
      it.shouldBeNull()
    }
  }

  @Test
  fun `emits delay time on start`() = runBlockingTest {
    otpRequestDisabler.submitDelay(60)
    otpRequestDisabler.disabledForSecondsFlow.take(1).collect {
      it shouldBeEqualTo 60
    }
  }

}
