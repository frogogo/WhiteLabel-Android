package ru.frogogo.whitelabel.data.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUiModel(
  val thumbUrl: String,
  val largeUrl: String,
) : Parcelable
