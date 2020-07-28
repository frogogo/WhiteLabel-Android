package ru.poprobuy.poprobuy

import androidx.lifecycle.Observer
import io.mockk.mockk
import retrofit2.Response
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.NetworkResource

fun <T> mockkObserver() = mockk<Observer<T>>(relaxed = true)

// Network
fun <T> successNetworkCall(result: T) = NetworkResource.Success<T, Any>(Response.success(result), result)

fun <T> failureNetworkCall() = NetworkResource.Error<T, Any>(null, NetworkError.Unknown())

fun <T> failureHttpNetworkCall(code: Int) = NetworkResource.Error<T, Any>(null, NetworkError.HttpError(code, null))
