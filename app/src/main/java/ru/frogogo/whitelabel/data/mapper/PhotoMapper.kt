package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.Photo
import ru.frogogo.whitelabel.data.model.ui.PhotoUiModel

fun Photo.toDomain(): PhotoUiModel =
  PhotoUiModel(
    thumbUrl = thumb,
    largeUrl = large,
  )
