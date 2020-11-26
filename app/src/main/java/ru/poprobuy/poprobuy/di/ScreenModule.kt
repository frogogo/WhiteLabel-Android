@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.ui.MainViewModel
import ru.poprobuy.poprobuy.ui.auth.code.AuthCodeViewModel
import ru.poprobuy.poprobuy.ui.auth.email.AuthEmailViewModel
import ru.poprobuy.poprobuy.ui.auth.name.AuthNameViewModel
import ru.poprobuy.poprobuy.ui.auth.phone.AuthPhoneViewModel
import ru.poprobuy.poprobuy.ui.auth.policy.AuthPolicyViewModel
import ru.poprobuy.poprobuy.ui.home.HomeViewModel
import ru.poprobuy.poprobuy.ui.machine_select.MachineSelectViewModel
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingViewModel
import ru.poprobuy.poprobuy.ui.products.MachineProductsViewModel
import ru.poprobuy.poprobuy.ui.products.select.ProductSelectionViewModel
import ru.poprobuy.poprobuy.ui.profile.ProfileViewModel
import ru.poprobuy.poprobuy.ui.profile.receipts.ReceiptsViewModel
import ru.poprobuy.poprobuy.ui.profile.receipts.details.ReceiptDetailsButtonState
import ru.poprobuy.poprobuy.ui.profile.receipts.details.ReceiptDetailsViewModel
import ru.poprobuy.poprobuy.ui.scanner.ScannerViewModel
import ru.poprobuy.poprobuy.ui.splash.SplashViewModel
import ru.poprobuy.poprobuy.ui.webview.WebViewViewModel

val screenModule = module {
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
  viewModel { HomeViewModel(get(), get()) }
  viewModel { (scanMode: ScanMode, receiptId: Int) ->
    ScannerViewModel(
      scanMode = scanMode,
      receiptId = receiptId,
      navigation = get(),
      createReceiptUseCase = get(),
      resourceProvider = get()
    )
  }
  viewModel { (receiptId: Int) -> MachineSelectViewModel(receiptId, get(), get()) }

  // Products
  viewModel { (receiptId: Int, machine: VendingMachineUiModel) ->
    MachineProductsViewModel(
      receiptId = receiptId,
      vendingMachine = machine,
      navigation = get(),
      productSelectionInteractor = get()
    )
  }
  viewModel { (params: ProductSelectionViewModel.Params) ->
    ProductSelectionViewModel(
      params = params,
      takeProductUseCase = get(),
      productSelectionInteractor = get()
    )
  }

  // Profile
  viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
  viewModel { ReceiptsViewModel(get(), get()) }
  viewModel { (buttonState: ReceiptDetailsButtonState) -> ReceiptDetailsViewModel(get(), buttonState) }
}
