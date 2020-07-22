@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.ui.auth.code.AuthCodeViewModel
import ru.poprobuy.poprobuy.ui.auth.email.AuthEmailViewModel
import ru.poprobuy.poprobuy.ui.auth.name.AuthNameViewModel
import ru.poprobuy.poprobuy.ui.auth.phone.AuthPhoneViewModel
import ru.poprobuy.poprobuy.ui.auth.policy.AuthPolicyViewModel
import ru.poprobuy.poprobuy.ui.home.HomeViewModel
import ru.poprobuy.poprobuy.ui.machine_select.MachineSelectViewModel
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingViewModel
import ru.poprobuy.poprobuy.ui.products.ProductsViewModel
import ru.poprobuy.poprobuy.ui.products.select.ProductSelectionInteractor
import ru.poprobuy.poprobuy.ui.products.select.ProductSelectionViewModel
import ru.poprobuy.poprobuy.ui.profile.ProfileViewModel
import ru.poprobuy.poprobuy.ui.profile.receipts.ReceiptsViewModel
import ru.poprobuy.poprobuy.ui.scanner.ScannerViewModel
import ru.poprobuy.poprobuy.ui.splash.SplashViewModel
import ru.poprobuy.poprobuy.ui.webview.WebViewViewModel

val screenModule = module {
  viewModel { SplashViewModel(get(), get()) }
  viewModel { OnboardingViewModel(get(), get()) }
  viewModel { WebViewViewModel() }

  // Auth
  viewModel { AuthPolicyViewModel(get(), get()) }
  viewModel { AuthPhoneViewModel(get(), get()) }
  viewModel { (phoneNumber: String) ->
    AuthCodeViewModel(
      phoneNumber = phoneNumber,
      navigation = get(),
      authenticationUseCase = get()
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
  viewModel { HomeViewModel(get()) }
  viewModel { (scanMode: ScanMode) ->
    ScannerViewModel(
      scanMode = scanMode,
      navigation = get()
    )
  }
  viewModel { MachineSelectViewModel(get()) }

  // Products
  viewModel { ProductsViewModel() }
  viewModel { ProductSelectionViewModel() }
  viewModel { ProductSelectionInteractor() }

  // Profile
  viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
  viewModel { ReceiptsViewModel(get()) }
}
