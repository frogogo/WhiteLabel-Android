package ru.poprobuy.poprobuy

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
open class RepositoryTest {

  @JvmField
  @RegisterExtension
  val coroutineTestExtension = CoroutinesTestExtension()

}
