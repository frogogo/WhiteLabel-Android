package ru.frogogo.whitelabel.ui.webview

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import ru.frogogo.whitelabel.extension.setVisible
import java.lang.ref.WeakReference

private const val MAX_PROGRESS = 100

class ProgressWebChromeClient(progressBar: ProgressBar) : WebChromeClient() {

  private val progressBarRef = WeakReference(progressBar)

  override fun onProgressChanged(view: WebView, newProgress: Int) {
    progressBarRef.get()?.apply {
      progress = newProgress
      setVisible(newProgress != MAX_PROGRESS)
    }
    super.onProgressChanged(view, newProgress)
  }
}
