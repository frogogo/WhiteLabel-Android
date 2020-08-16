package ru.poprobuy.poprobuy.util.network

import retrofit2.Response
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse

inline fun <reified T> apiCall(block: () -> Response<T>): NetworkResource<T, ErrorResponse> = safeApiCall(block)
