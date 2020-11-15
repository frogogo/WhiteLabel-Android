package ru.poprobuy.poprobuy.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.poprobuy.poprobuy.util.Event

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T> LiveData<T>.isEmpty() = value == null

/**
 * Post [Event] with state to main thread
 * @param event State Event
 */
fun <T> MutableLiveData<Event<T>>.postEvent(event: T) {
  this.postValue(Event(event))
}

/**
 * Post [Unit] event to LiveData
 */
fun MutableLiveData<Event<Unit>>.postEvent() {
  this.postValue(Event(Unit))
}

/**
 * Set [Event] with state in current thread
 * @param event State Event
 */
fun <T> MutableLiveData<Event<T>>.setEvent(event: T) {
  this.value = Event(event)
}
