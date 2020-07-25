package ru.poprobuy.poprobuy.arch.ui

import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.d
import ru.poprobuy.poprobuy.extension.asLiveData
import kotlin.properties.Delegates

abstract class StateViewModel<State : BaseViewState, Action : BaseAction>(initialState: State) : BaseViewModel() {

  private val _stateLiveData = MutableLiveData<State>()
  val stateLiveData = _stateLiveData.asLiveData()

  protected var state by Delegates.observable(initialState) { _, _, new ->
    _stateLiveData.value = new
  }

  fun sendAction(action: Action) {
    d { "Sending action - $action" }
    state = onReduceState(action)
  }

  protected abstract fun onReduceState(action: Action): State

}
