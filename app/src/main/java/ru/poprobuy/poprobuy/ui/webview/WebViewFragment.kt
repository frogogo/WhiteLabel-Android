package ru.poprobuy.poprobuy.ui.webview

import android.annotation.SuppressLint
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentWebViewBinding
import ru.poprobuy.poprobuy.extension.setOnClickListener
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen

class WebViewFragment : BaseFragment<WebViewViewModel>(
  layoutId = R.layout.fragment_web_view,
  screen = AnalyticsScreen.WEB_VIEW
) {

  override val viewModel: WebViewViewModel by viewModel()

  private val args: WebViewFragmentArgs by navArgs()
  private val binding: FragmentWebViewBinding by viewBinding()

  override fun initViews() {
    binding.apply {
      buttonBack.setOnClickListener(viewModel::navigateBack)
      textViewTitle.text = getString(args.titleResId)
    }
    initWebView()
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun initWebView() {
    binding.webView.apply {
      settings.apply {
        javaScriptEnabled = true
        domStorageEnabled = true
      }

      webChromeClient = ProgressWebChromeClient(binding.progressBar)
      webViewClient = WebClient()
      loadUrl(args.url)
    }
  }

}
