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

  open fun onCreate() = Unit

  open fun onStart() = Unit

  open fun onStop() = Unit

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

}
