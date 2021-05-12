@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.frogogo.whitelabel.ui.MainViewModel
import ru.frogogo.whitelabel.ui.auth.code.AuthCodeViewModel
import ru.frogogo.whitelabel.ui.auth.email.AuthEmailViewModel
import ru.frogogo.whitelabel.ui.auth.name.AuthNameViewModel
import ru.frogogo.whitelabel.ui.auth.phone.AuthPhoneViewModel
import ru.frogogo.whitelabel.ui.auth.policy.AuthPolicyViewModel
import ru.frogogo.whitelabel.ui.onboarding.OnboardingViewModel
import ru.frogogo.whitelabel.ui.profile.ProfileViewModel
import ru.frogogo.whitelabel.ui.profile.receipts.ReceiptsViewModel
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsButtonState
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsViewModel
import ru.frogogo.whitelabel.ui.scanner.ScannerViewModel
import ru.frogogo.whitelabel.ui.splash.SplashViewModel
import ru.frogogo.whitelabel.ui.webview.WebViewViewModel
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragmentCallbackViewModel

@Deprecated("Use scopes")
val screenModuleLegacy = module {
  viewModel { MainViewModel(get(), get()) }
  viewModel { SplashViewModel(get(), get()) }
  viewModel { OnboardingViewModel(get(), get()) }
  viewModel { WebViewViewModel() }

  // Auth
  viewModel { AuthPolicyViewModel(get(), get()) }
  viewModel { (showLogoutDialog: Boolean) -> AuthPhoneViewModel(showLogoutDialog, get(), get()) }
  viewModel { (phoneNumber: String) ->
    AuthCodeViewModel(
      phoneNumber = phoneNumber,
      navigation = get(),
      authenticationUseCase = get(),
      requestConfirmationCodeUseCase = get(),
      otpRequestDisabler = get()
    )
  }
  viewModel { AuthNameViewModel(get()) }
  viewModel { (userName: String) ->
    AuthEmailViewModel(
      userName = userName,
      navigation = get(),
      authRepository = get(),
      updateUserDetailsUseCase = get()
    )
  }

  // Home
  viewModel { ScannerViewModel(get(), get()) }

  // Profile
  viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
  viewModel { ReceiptsViewModel(get(), get()) }
  viewModel { (buttonState: ReceiptDetailsButtonState) -> ReceiptDetailsViewModel(get(), buttonState) }

  // Stuff
  viewModel { ErrorDialogFragmentCallbackViewModel() }
}
