package ru.frogogo.test.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.RegisterExtension
import ru.frogogo.test.rule.CoroutinesTestExtension
import ru.frogogo.test.rule.InstantTaskExecutorExtension

@ExperimentalCoroutinesApi
open class ViewModelTestJUnit5 {

  @JvmField
  @RegisterExtension
  val instantExecutorExtension = InstantTaskExecutorExtension()

  @JvmField
  @RegisterExtension
  val coroutineTestExtension = CoroutinesTestExtension()
}
