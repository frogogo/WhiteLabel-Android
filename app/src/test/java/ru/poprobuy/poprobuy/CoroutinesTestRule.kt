package ru.poprobuy.poprobuy

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutinesTestRule : TestWatcher(), CoroutinesTestHelper by CoroutinesTestHelperImpl() {

  override fun starting(description: Description) {
    super.starting(description)
    start()
  }

  override fun finished(description: Description) {
    super.finished(description)
    finish()
  }

}
