package ru.poprobuy.poprobuy.ui.machine_select

import androidx.annotation.StringRes

sealed class MachineSelectCommand {
  data class DialogError(val error: String?) : MachineSelectCommand()
  data class NumberValidationError(@StringRes val errorResId: Int?) : MachineSelectCommand()
}
