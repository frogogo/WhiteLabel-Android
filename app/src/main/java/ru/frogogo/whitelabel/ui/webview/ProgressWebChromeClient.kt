package ru.frogogo.whitelabel.ui.webview

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import ru.frogogo.whitelabel.extension.setVisible
import java.lang.ref.WeakReference

class ProgressWebChromeClient(progressBar: ProgressBar) : WebChromeClient() {

  private val progressBarRef = WeakReference(progressBar)

  override fun onProgressChanged(view: WebView, newProgress: Int) {
    progressBarRef.get()?.apply {
      progress = newProgress
      setVisible(newProgress != 100)
    }
    super.onProgressChanged(view, newProgress)
  }
}
