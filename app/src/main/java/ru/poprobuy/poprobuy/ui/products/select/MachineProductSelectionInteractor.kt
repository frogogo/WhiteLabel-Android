package ru.poprobuy.poprobuy.ui.products.select

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull

// TODO: 26.11.2020 Tests
class MachineProductSelectionInteractor {

  private val _commandEvent = MutableSharedFlow<Command?>()
  val commandEvent: Flow<Command> = _commandEvent.asSharedFlow().filterNotNull()

  suspend fun issueCommand(command: Command) {
    _commandEvent.emit(command)
  }

  suspend fun clearState() {
    _commandEvent.emit(null)
  }

  sealed class Command {
    object NavigateToHome : Command()
    data class ShowErrorDialog(val error: String) : Command()
  }
}
