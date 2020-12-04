package ru.poprobuy.poprobuy.util.network

import retrofit2.Response
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse

suspend fun <T> apiCall(block: suspend () -> Response<T>): NetworkResource<T, ErrorResponse> = safeApiCall(block)
