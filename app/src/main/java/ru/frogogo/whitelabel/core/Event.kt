package ru.frogogo.whitelabel.core

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
data class Event<out T>(
  private val content: T,
  private var hasBeenHandled: Boolean = false,
) {

  /**
   * Returns the content and prevents its use again.
   */
  fun getContentIfNotHandled(): T? {
    return if (hasBeenHandled) {
      null
    } else {
      hasBeenHandled = true
      content
    }
  }

  /**
   * Returns the content, even if it's already been handled.
   */
  fun peekContent(): T = content
}

@MainThread
inline fun <T> LiveData<Event<T>>.observeEvent(
  owner: LifecycleOwner,
  crossinline onChanged: (T) -> Unit,
): Observer<Event<T>> {
  val wrappedObserver = Observer<Event<T>> { event ->
    event.getContentIfNotHandled()?.let(onChanged)
  }
  observe(owner, wrappedObserver)
  return wrappedObserver
}
