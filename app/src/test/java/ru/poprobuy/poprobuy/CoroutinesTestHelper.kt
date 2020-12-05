package ru.poprobuy.poprobuy

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider

@ExperimentalCoroutinesApi
interface CoroutinesTestHelper {

  val dispatcher: TestCoroutineDispatcher
  val testDispatcherProvider: DispatchersProvider

  fun start()

  fun finish()

  fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit)

  fun enableTestDispatchers()

  fun disableTestDispatchers()

}

@ExperimentalCoroutinesApi
internal class CoroutinesTestHelperImpl(
  override val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
) : CoroutinesTestHelper {

  private var useTestDispatchers = true

  override val testDispatcherProvider: DispatchersProvider = object : DispatchersProvider {
    override val main: CoroutineDispatcher
      get() = if (useTestDispatchers) dispatcher else Dispatchers.Main
    override val default: CoroutineDispatcher
      get() = if (useTestDispatchers) dispatcher else Dispatchers.Default
    override val io: CoroutineDispatcher
      get() = if (useTestDispatchers) dispatcher else Dispatchers.IO
    override val unconfined: CoroutineDispatcher
      get() = if (useTestDispatchers) dispatcher else Dispatchers.Unconfined
  }

  override fun start() {
    Dispatchers.setMain(dispatcher)
  }

  override fun finish() {
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  override fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
    dispatcher.runBlockingTest { block() }
  }

  override fun enableTestDispatchers() {
    useTestDispatchers = true
  }

  override fun disableTestDispatchers() {
    useTestDispatchers = false
  }

}
