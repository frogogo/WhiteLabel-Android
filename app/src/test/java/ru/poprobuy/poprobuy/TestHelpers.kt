package ru.poprobuy.poprobuy

import androidx.lifecycle.Observer
import io.mockk.mockk
import retrofit2.Response
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.NetworkResource

fun <T> mockkObserver() = mockk<Observer<T>>(relaxed = true)

fun NetworkError.Companion.testError(code: Int = 400): NetworkError<ErrorResponse> =
  NetworkError.HttpError(code, null)

// Network
fun <T, E> successNetworkCall(result: T): NetworkResource.Success<T, E> =
  NetworkResource.Success<T, E>(Response.success(result), result)

fun <T, E> failureNetworkCall(): NetworkResource.Error<T, E> =
  NetworkResource.Error(null, NetworkError.Unknown())

fun <T, E> failureHttpNetworkCall(code: Int): NetworkResource<T, E> =
  NetworkResource.Error<T, E>(null, NetworkError.HttpError(code, null))
