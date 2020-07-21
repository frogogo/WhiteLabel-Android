package ru.poprobuy.poprobuy.arch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import ru.poprobuy.poprobuy.arch.navigation.AppNavigation
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

open class BaseViewModel : ViewModel() {

  private val _navigationLiveEvent = LiveEvent<NavigationCommand>()
  internal val navigationLiveEvent: LiveData<NavigationCommand> = _navigationLiveEvent

  private val _baseCommandLiveEvent = LiveEvent<BaseCommand>()
  val baseCommandLiveEvent: LiveData<BaseCommand> get() = _baseCommandLiveEvent

  open fun onCreate(): Unit = Unit

  open fun onStart(): Unit = Unit

  open fun onStop(): Unit = Unit

  /**
   * Convenient method to handle navigation from a [ViewModel]
   */
  fun NavigationCommand.navigate() {
    _navigationLiveEvent.postValue(this)
  }

  /**
   * Executes back navigation
   */
  fun navigateBack() {
    d { "Navigating back" }
    AppNavigation.navigateBack().navigate()
  }

  fun hideKeyboard() {
    _baseCommandLiveEvent.postValue(BaseCommand.HideKeyboard)
  }

}
