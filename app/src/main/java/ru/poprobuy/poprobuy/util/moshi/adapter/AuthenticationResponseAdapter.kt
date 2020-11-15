package ru.poprobuy.poprobuy.util.moshi.adapter

import com.squareup.moshi.FromJson
import ru.poprobuy.poprobuy.data.mapper.toResponse
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponseJson

class AuthenticationResponseAdapter {

  @FromJson
  fun fromJson(response: AuthenticationResponseJson): AuthenticationResponse = response.toResponse()

}
