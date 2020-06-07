package ru.poprobuy.poprobuy.arch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

open class BaseViewModel : ViewModel() {

  private val _navigationLive = LiveEvent<NavigationCommand>()
  internal val navigationLive: LiveData<NavigationCommand> = _navigationLive

  open fun onCreate() = Unit

  open fun onStart() = Unit

  /**
   * Convenient method to handle navigation from a [ViewModel]
   */
  fun NavigationCommand.navigate() {
    _navigationLive.postValue(this)
  }

}
