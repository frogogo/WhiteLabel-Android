package ru.poprobuy.poprobuy.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider

@DslMarker
private annotation class Marker

open class Repository(
  private val dispatchers: DispatchersProvider,
) {

  @Marker
  protected suspend fun <T> withIOContext(block: suspend CoroutineScope.() -> T): T = withContext(dispatchers.io, block)
}
