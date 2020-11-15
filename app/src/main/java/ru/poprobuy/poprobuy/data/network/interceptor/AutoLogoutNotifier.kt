package ru.poprobuy.poprobuy.data.network.interceptor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.poprobuy.poprobuy.analytics.SystemEvents
import ru.poprobuy.poprobuy.extension.postEvent
import ru.poprobuy.poprobuy.util.Event
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager

class AutoLogoutNotifier(
  private val analyticsManager: AnalyticsManager,
) {

  private val _logoutEvent = MutableLiveData<Event<Unit>>()
  val logoutEvent: LiveData<Event<Unit>> get() = _logoutEvent

  internal fun logout() {
    analyticsManager.logEvent(SystemEvents.AutoLogout)
    _logoutEvent.postEvent()
  }

}
