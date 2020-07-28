package ru.poprobuy.poprobuy.ui.webview

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WebClient : WebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
    view.loadUrl(request.url.toString())
    return true
  }

}
