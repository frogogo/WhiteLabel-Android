package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel

fun User.toProfileModel(appVersion: String): ProfileUiModel = ProfileUiModel(
  name = firstName,
  email = email,
  phoneNumber = phoneNumber,
  appVersion = appVersion
)
