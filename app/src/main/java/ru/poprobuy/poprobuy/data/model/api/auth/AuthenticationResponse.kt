package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.JsonClass
import ru.poprobuy.poprobuy.data.model.api.user.User

@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
  val user: User?,
  val accessToken: String,
  val refreshToken: String,
  val isNew: Boolean,
)
