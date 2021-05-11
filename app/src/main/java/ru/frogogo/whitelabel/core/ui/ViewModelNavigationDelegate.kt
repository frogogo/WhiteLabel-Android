package ru.frogogo.whitelabel.core.ui

import kotlinx.coroutines.flow.Flow
import ru.frogogo.whitelabel.core.navigation.NavigationCommand

interface ViewModelNavigationDelegate {

  val navigationCommandFlow: Flow<NavigationCommand>
}
