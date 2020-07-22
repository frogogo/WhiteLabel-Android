package ru.poprobuy.poprobuy.data.model.ui.profile

import ru.poprobuy.poprobuy.data.model.api.user.User

data class ProfileUiModel(
  val name: String,
  val email: String,
  val phoneNumber: String,
  val appVersion: String
)

fun User.toProfileModel(appAbout: String): ProfileUiModel = ProfileUiModel(
  name = firstName,
  email = email,
  phoneNumber = phoneNumber,
  appVersion = appAbout
)
