package ru.poprobuy.poprobuy.data.network.interceptor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ru.poprobuy.poprobuy.analytics.SystemEvents
import ru.poprobuy.poprobuy.extension.postEvent
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager

class AutoLogoutNotifier(
  private val analyticsManager: AnalyticsManager,
) {

  private val _logoutEvent = MutableLiveData<Event<Unit>>()
  val logoutEvent: LiveData<Event<Unit>> get() = _logoutEvent

  internal fun logout() {
    e(Throwable()) { "Logout occurred" }
    analyticsManager.logEvent(SystemEvents.AutoLogout)
    _logoutEvent.postEvent()
  }
}
