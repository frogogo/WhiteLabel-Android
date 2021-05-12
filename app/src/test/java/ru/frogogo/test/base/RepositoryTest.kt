package ru.frogogo.test.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.RegisterExtension
import ru.frogogo.test.rule.CoroutinesTestExtension

@ExperimentalCoroutinesApi
open class RepositoryTest {

  @JvmField
  @RegisterExtension
  val coroutineTestExtension = CoroutinesTestExtension()
}
