package ru.poprobuy.poprobuy

import androidx.lifecycle.Observer
import io.mockk.clearMocks
import io.mockk.mockk
import retrofit2.Response
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.util.Event
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.NetworkResource

fun <T> mockkObserver() = mockk<Observer<T>>(relaxed = true)

fun <T> mockkEventObserver() = mockk<Observer<Event<T>>>(relaxed = true)

fun <T> Observer<Event<T>>.onEventChanged(event: T) {
  onChanged(Event(event))
}

fun Any.clearRecordedCalls() = clearMocks(
  this,
  answers = false,
  recordedCalls = true,
  childMocks = false,
  verificationMarks = false,
  exclusionRules = false
)

fun NetworkError.Companion.testError(code: Int = 400): NetworkError<ErrorResponse> =
  NetworkError.HttpError(code, null)

fun NetworkError.Companion.test422Error(errorReason: String): NetworkError<ErrorResponse> =
  NetworkError.HttpError(422, ErrorResponse(errorReason, errorReason))

// Network
fun <T, E> successNetworkCall(result: T): NetworkResource.Success<T, E> =
  NetworkResource.Success<T, E>(Response.success(result), result)

fun <T, E> failureNetworkCall(): NetworkResource.Error<T, E> =
  NetworkResource.Error(null, NetworkError.Unknown())

fun <T, E> failureHttpNetworkCall(code: Int): NetworkResource<T, E> =
  NetworkResource.Error<T, E>(null, NetworkError.HttpError(code, null))
