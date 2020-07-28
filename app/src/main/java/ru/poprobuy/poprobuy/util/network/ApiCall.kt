package ru.poprobuy.poprobuy.util.network

import retrofit2.Response

inline fun <T> apiCall(block: () -> Response<T>) = safeApiCall<T, Any>(block)
