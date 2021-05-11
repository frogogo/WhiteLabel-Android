package ru.frogogo.whitelabel.core.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import kotlin.coroutines.CoroutineContext

open class BaseViewModelDelegate(
  dispatchers: DispatchersProvider,
) {

  protected val scope: CoroutineScope by lazy {
    scopeInitialized = true
    CoroutineScope(coroutineContext)
  }

  private val coroutineContext: CoroutineContext by lazy { dispatchers.main + SupervisorJob() }
  private var scopeInitialized = false

  fun cancelJob() {
    if (!scopeInitialized) {
      return
    }
    coroutineContext.cancelChildren()
  }
}
