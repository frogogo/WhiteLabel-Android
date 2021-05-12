package ru.frogogo.whitelabel.util

import io.mockk.clearAllMocks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.frogogo.test.rule.CoroutinesTestExtension

@ExperimentalCoroutinesApi
class OtpRequestDisablerTest {

  @JvmField
  @RegisterExtension
  val coroutineTestExtension = CoroutinesTestExtension()

  private lateinit var otpRequestDisabler: OtpRequestDisabler

  @BeforeEach
  fun setUp() {
    otpRequestDisabler = OtpRequestDisabler()
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
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
