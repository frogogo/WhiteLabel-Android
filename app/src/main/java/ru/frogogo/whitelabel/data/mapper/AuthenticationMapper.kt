package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponse
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponseJson
import ru.frogogo.whitelabel.data.model.api.user.User

fun AuthenticationResponseJson.toResponse(): AuthenticationResponse = AuthenticationResponse(
  user = run {
    User(
      email = email ?: return@run null,
      firstName = firstName ?: return@run null,
      phoneNumber = phoneNumber
    )
  },
  accessToken = jwt,
  refreshToken = refreshToken,
  isNew = isNew
)
