package ru.frogogo.whitelabel.util.moshi.adapter

import com.squareup.moshi.FromJson
import ru.frogogo.whitelabel.data.mapper.toResponse
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponse
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponseJson

object AuthenticationResponseAdapter {

  @FromJson
  fun fromJson(response: AuthenticationResponseJson): AuthenticationResponse = response.toResponse()
}
