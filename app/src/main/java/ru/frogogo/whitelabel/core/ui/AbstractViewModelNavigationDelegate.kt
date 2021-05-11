package ru.frogogo.whitelabel.core.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

abstract class AbstractViewModelNavigationDelegate(
  dispatchersProvider: DispatchersProvider,
) : BaseViewModelDelegate(dispatchersProvider),
  ViewModelNavigationDelegate {

  override val navigationCommandFlow: Flow<NavigationCommand>
    get() = mutableNavigationFlow

  private val mutableNavigationFlow = MutableSharedFlow<NavigationCommand>()

  fun NavigationCommand.navigate() {
    scope.launch {
      mutableNavigationFlow.emit(this@navigate)
    }
  }
}
