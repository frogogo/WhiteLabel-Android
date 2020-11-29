package ru.poprobuy.poprobuy.core.ui

import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import ru.poprobuy.poprobuy.core.navigation.AppNavigation
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.asLiveData

open class BaseViewModel : ViewModel() {

  private val _navigationLiveEvent = LiveEvent<NavigationCommand>()
  internal val navigationLiveEvent = _navigationLiveEvent.asLiveData()

  private val _baseCommandLiveEvent = LiveEvent<BaseCommand>()
  val baseCommandLiveEvent = _baseCommandLiveEvent.asLiveData()

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
