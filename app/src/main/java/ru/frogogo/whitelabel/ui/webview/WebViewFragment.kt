package ru.frogogo.whitelabel.ui.webview

import android.annotation.SuppressLint
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentWebViewBinding
import ru.frogogo.whitelabel.extension.setOnClickListener
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class WebViewFragment : BaseFragment<WebViewViewModel>() {

  override val viewModel: WebViewViewModel by viewModel()

  private val args: WebViewFragmentArgs by navArgs()
  private val binding: FragmentWebViewBinding by viewBinding()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_web_view,
    screen = AnalyticsScreen.WEB_VIEW,
  )

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
