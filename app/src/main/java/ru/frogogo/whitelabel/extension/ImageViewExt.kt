package ru.frogogo.whitelabel.extension

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.github.ajalt.timberkt.e
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.journeyapps.barcodescanner.BarcodeEncoder

fun ImageView.showQrCode(code: String) {
  showBarCodeImpl(
    code,
    BarcodeFormat.QR_CODE,
    width,
    width
  )
}

fun ImageView.showBarcode(code: String) {
  showBarCodeImpl(
    code,
    BarcodeFormat.CODE_128,
    width,
    (width * 0.4F).toInt()
  )
}

private fun ImageView.showBarCodeImpl(
  code: String,
  format: BarcodeFormat,
  width: Int,
  height: Int,
) {
  runCatching {
    val barcodeEncoder = BarcodeEncoder()
    val matrix = barcodeEncoder.encode(
      code,
      format,
      width,
      height,
      mapOf(EncodeHintType.MARGIN to 1) // Reduces margin
    )
    val bitmap = barcodeEncoder.createBitmap(matrix)
    val drawable = BitmapDrawable(resources, bitmap)

    setImageDrawable(drawable)
  }.onFailure { e(it) }
}
