package ru.poprobuy.poprobuy.ui.machine_select

import androidx.annotation.StringRes

sealed class MachineSelectCommand {
  data class ShowDialogError(val error: String?) : MachineSelectCommand()
  data class ShowFieldError(@StringRes val errorResId: Int?) : MachineSelectCommand()
}
