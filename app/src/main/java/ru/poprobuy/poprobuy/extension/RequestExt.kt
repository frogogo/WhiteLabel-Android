package ru.poprobuy.poprobuy.extension

import androidx.annotation.VisibleForTesting
import okhttp3.Request

private const val AUTHORIZATION_HEADER = "Authorization"

@VisibleForTesting
const val BEARER = "Bearer"

fun Request.Builder.addAuthHeader(
  token: String,
  scheme: String = BEARER,
): Request.Builder = header(AUTHORIZATION_HEADER, "$scheme $token")
