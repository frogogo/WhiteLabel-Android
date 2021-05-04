package ru.frogogo.whitelabel.ui.scanner

sealed class ScannerEffect {

  object ToggleFlash : ScannerEffect()

  object ShowSuccess : ScannerEffect()

  data class ShowError(
    val error: String?,
  ) : ScannerEffect()
}
