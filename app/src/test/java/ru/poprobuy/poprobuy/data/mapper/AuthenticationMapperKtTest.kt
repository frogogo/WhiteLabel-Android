package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponseJson

internal class AuthenticationMapperKtTest {

  @Test
  fun `mapper should convert json response to response`() {
    val response = DataFixtures.authenticationResponse
    val jsonResponse = AuthenticationResponseJson(
      email = response.user.email,
      firstName = response.user.firstName,
      phoneNumber = response.user.phoneNumber,
      refreshToken = response.refreshToken,
      jwt = response.accessToken,
      isNew = response.isNew
    )

    jsonResponse.toResponse() shouldBeEqualTo DataFixtures.authenticationResponse
  }

}
