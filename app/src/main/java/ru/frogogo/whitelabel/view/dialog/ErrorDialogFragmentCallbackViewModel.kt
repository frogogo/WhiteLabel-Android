package ru.frogogo.whitelabel.view.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.extension.postEvent

class ErrorDialogFragmentCallbackViewModel : ViewModel() {

  private val _onDismissEvent = MutableLiveData<Event<Int>>()
  val onDismissEvent = _onDismissEvent.asLiveData()

  fun onDismiss(dialogId: Int) {
    d { "Dismissing dialog with id=$dialogId" }
    _onDismissEvent.postEvent(dialogId)
  }
}
