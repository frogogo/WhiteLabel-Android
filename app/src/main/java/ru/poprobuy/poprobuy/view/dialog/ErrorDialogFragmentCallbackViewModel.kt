package ru.poprobuy.poprobuy.view.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.postEvent
import ru.poprobuy.poprobuy.core.Event

class ErrorDialogFragmentCallbackViewModel : ViewModel() {

  private val _onDismissEvent = MutableLiveData<Event<Int>>()
  val onDismissEvent = _onDismissEvent.asLiveData()

  fun onDismiss(dialogId: Int) {
    d { "Dismissing dialog with id=$dialogId" }
    _onDismissEvent.postEvent(dialogId)
  }

}
