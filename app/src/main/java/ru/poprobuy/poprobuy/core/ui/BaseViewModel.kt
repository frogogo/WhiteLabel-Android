package ru.poprobuy.poprobuy.core.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.core.navigation.AppNavigation
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.postEvent
import ru.poprobuy.poprobuy.extension.setEvent
import ru.poprobuy.poprobuy.core.Event

open class BaseViewModel : ViewModel() {

  private val _navigationLiveEvent = MutableLiveData<Event<NavigationCommand>>()
  internal val navigationLiveEvent = _navigationLiveEvent.asLiveData()

  private val _baseCommandLiveEvent = MutableLiveData<Event<BaseCommand>>()
  val baseCommandLiveEvent = _baseCommandLiveEvent.asLiveData()

  open fun onCreate(): Unit = Unit

  open fun onStart(): Unit = Unit

  open fun onStop(): Unit = Unit

  /**
   * Convenient method to handle navigation from a [ViewModel]
   */
  fun NavigationCommand.navigate() {
    _navigationLiveEvent.postEvent(this)
  }

  /**
   * Executes back navigation
   */
  fun navigateBack() {
    d { "Navigating back" }
    AppNavigation.navigateBack().navigate()
  }

  fun hideKeyboard() {
    viewModelScope.launch {
      _baseCommandLiveEvent.setEvent(BaseCommand.HideKeyboard)
    }
  }
}
