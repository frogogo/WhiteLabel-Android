package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.model.ui.profile.ProfileUiModel

fun User.toProfileModel(appVersion: String): ProfileUiModel = ProfileUiModel(
  name = firstName,
  email = email,
  phoneNumber = phoneNumber,
  appVersion = appVersion,
)
