package ru.poprobuy.poprobuy.ui.webview

import android.annotation.SuppressLint
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentWebViewBinding
import ru.poprobuy.poprobuy.extension.setOnClickListener

class WebViewFragment : BaseFragment<WebViewViewModel>(R.layout.fragment_web_view) {

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
