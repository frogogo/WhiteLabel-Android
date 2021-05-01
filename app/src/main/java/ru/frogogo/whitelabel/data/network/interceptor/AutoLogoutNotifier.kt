package ru.frogogo.whitelabel.data.network.interceptor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import ru.frogogo.whitelabel.analytics.SystemEvents
import ru.frogogo.whitelabel.extension.postEvent
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.util.analytics.AnalyticsManager

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
