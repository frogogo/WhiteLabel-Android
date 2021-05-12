package ru.frogogo.whitelabel.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

open class Repository(
  private val dispatchers: DispatchersProvider,
) {

  protected suspend fun <T> withIOContext(block: suspend CoroutineScope.() -> T): T = withContext(dispatchers.io, block)
}
