package ru.frogogo.test.rule

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class CoroutinesTestExtension : CoroutinesTestHelper by CoroutinesTestHelperImpl(),
  BeforeEachCallback, AfterEachCallback {

  override fun beforeEach(context: ExtensionContext) {
    start()
  }

  override fun afterEach(context: ExtensionContext) {
    finish()
  }
}
