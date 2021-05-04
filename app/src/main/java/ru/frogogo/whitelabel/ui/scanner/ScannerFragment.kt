package ru.frogogo.whitelabel.ui.scanner

import android.Manifest
import android.content.DialogInterface
import android.view.View
import androidx.core.view.doOnLayout
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.journeyapps.barcodescanner.Size
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentScannerBinding
import ru.frogogo.whitelabel.extension.*
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment.Companion.showIn
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragmentCallbackViewModel

class ScannerFragment : BaseFragment<ScannerViewModel>(),
  AndroidScopeComponent,
  BarcodeCallback {

  override val scope: Scope by fragmentScope()
  override val viewModel: ScannerViewModel by viewModel()

  private val errorDialogFragmentCallbackViewModel: ErrorDialogFragmentCallbackViewModel by sharedViewModel()
  private val binding: FragmentScannerBinding by viewBinding()
  private var flashIsOn = false

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_scanner,
    screen = AnalyticsScreen.SCANNER,
    statusBarColor = R.color.transparent,
    fullscreen = true,
    lightStatusBar = false
  )

  override fun initViews() {
    // Move toolbar down as activity is in fullscreen mode
    binding.apply {
      layoutToolbar.updateMargin(top = requireActivity().getStatusBarHeight())
      buttonFlash.setOnClickListener { viewModel.onFlashButtonClicked() }
      buttonClose.setOnSafeClickListener(viewModel::onBackButtonClicked)
      buttonHelp.setOnSafeClickListener(viewModel::onHelpButtonClicked)
    }

    initScanner()
  }

  override fun initObservers() {
    with(viewModel) {
      observe(isLoadingLive, binding.progressBar::setVisible)
      observe(effectEvent, ::handleEffect)
    }
    observeEvent(errorDialogFragmentCallbackViewModel.onDismissEvent) { dialogId ->
      if (dialogId == ERROR_DIALOG_ID) binding.barcodeView.resume()
    }
  }

  override fun onStart() {
    super.onStart()
    checkPermissions()
  }

  override fun onResume() {
    super.onResume()
    binding.barcodeView.resume()
  }

  override fun onPause() {
    super.onPause()
    binding.barcodeView.pause()
  }

  override fun barcodeResult(result: BarcodeResult) {
    i { "QR code handled - $result" }
    requireContext().vibratePhone()
    binding.barcodeView.pause()
    viewModel.createReceipt(result.text)
  }

  override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>): Unit = Unit

  private fun initScanner() {
    binding.barcodeView.apply {
      // Title
      setStatusText(getString(R.string.scanner_title_receipt))
      // Decoder
      barcodeView.decoderFactory = DefaultDecoderFactory(BARCODE_FORMATS)
      // Size
      doOnLayout { view ->
        val width = (view.width * 0.82).toInt()
        val height = (width * 0.95).toInt()
        barcodeView.framingRectSize = Size(width, height)
        findViewById<View>(R.id.view_surface_edges).setSize(width, height)
      }
    }
  }

  private fun checkPermissions() {
    withPermission(
      permission = Manifest.permission.CAMERA,
      onNeverAskAgain = {
        e { "Permission request denied, never ask again" }
        alert {
          setTitle(R.string.scanner_permissions_denied_title)
          setMessage(R.string.scanner_permissions_denied_settings)
          setPositiveButton(R.string.common_button_ok) { _, _ -> context.showAppDetailsSettings() }
          setNegativeButton(R.string.common_button_cancel) { dialog: DialogInterface, _ ->
            dialog.cancel()
            viewModel.navigateBack()
          }
          setCancelable(false)
        }
      },
      onPermissionDenied = {
        e { "Permission request denied" }
        alert {
          setMessage(R.string.scanner_permissions_denied_title)
          setPositiveButton(R.string.common_button_ok) { _, _ -> checkPermissions() }
          setNegativeButton(R.string.common_button_cancel) { dialog: DialogInterface, _ ->
            dialog.cancel()
            viewModel.navigateBack()
          }
          setCancelable(false)
        }
      },
      onPermissionGranted = {
        d { "Permission granted" }
        binding.barcodeView.decodeContinuous(this)
      }
    )
  }

  private fun handleEffect(effect: ScannerEffect) {
    @Exhaustive
    when(effect) {
      is ScannerEffect.ShowError -> showErrorDialog(effect.error)
      ScannerEffect.ToggleFlash -> toggleFlash()
      ScannerEffect.ShowSuccess -> TODO()
    }
  }

  private fun showErrorDialog(error: String?) {
    ErrorDialogFragment.newInstance(error, ERROR_DIALOG_ID)
      .showIn(childFragmentManager)
  }

  private fun toggleFlash() {
    flashIsOn = !flashIsOn
    binding.apply {
      buttonFlash.setImageResource(if (flashIsOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off)
      if (flashIsOn) barcodeView.setTorchOn() else barcodeView.setTorchOff()
    }
  }

  companion object {
    private val BARCODE_FORMATS = listOf(BarcodeFormat.QR_CODE)
    private val ERROR_DIALOG_ID = ErrorDialogFragment.getDialogId()
  }
}
