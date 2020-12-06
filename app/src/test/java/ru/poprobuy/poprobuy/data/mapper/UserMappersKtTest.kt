package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel

class UserMappersKtTest {

  @Test
  fun `user maps to profile model`() {
    val user = DataFixtures.user
    val appVersion = "1.0.0 (10)"
    user.toProfileModel(appVersion) shouldBeEqualTo ProfileUiModel(
      name = user.firstName,
      phoneNumber = user.phoneNumber,
      email = user.email,
      appVersion = appVersion
    )
  }

}
