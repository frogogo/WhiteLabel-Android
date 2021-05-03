package ru.frogogo.whitelabel.extension

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.github.ajalt.timberkt.e
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.journeyapps.barcodescanner.BarcodeEncoder

fun ImageView.showQrCode(code: String) {
  runCatching {
    val barcodeEncoder = BarcodeEncoder()
    val bitMatrix = barcodeEncoder.encode(
      code,
      BarcodeFormat.QR_CODE,
      width,
      width,
      mapOf(EncodeHintType.MARGIN to 1) // Reduces margin
    ).run { barcodeEncoder.createBitmap(this) }
    val barCodeDrawable = BitmapDrawable(resources, bitMatrix)
    setImageDrawable(barCodeDrawable)
  }.onFailure { e(it) }
}
