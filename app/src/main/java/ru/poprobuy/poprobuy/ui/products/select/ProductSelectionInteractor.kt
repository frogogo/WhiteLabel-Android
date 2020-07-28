package ru.poprobuy.poprobuy.ui.products.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel

class ProductSelectionInteractor : BaseViewModel() {

  // FIXME: 11.07.2020 Use Event
  private val commandLive = MutableLiveData<ProductSelectionCommand>()

  fun getCommandEvent(): LiveData<ProductSelectionCommand> {
    return commandLive
  }

  fun issueCommand(command: ProductSelectionCommand) {
    commandLive.value = command
  }

}
