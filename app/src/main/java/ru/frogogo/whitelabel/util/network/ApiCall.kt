package ru.frogogo.whitelabel.util.network

import retrofit2.Response
import ru.frogogo.whitelabel.data.model.api.ErrorResponse

suspend fun <T> apiCall(block: suspend () -> Response<T>): NetworkResource<T, ErrorResponse> = safeApiCall(block)
