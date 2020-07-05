@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.poprobuy.poprobuy.ui.auth.code.AuthCodeConfirmationViewModel
import ru.poprobuy.poprobuy.ui.auth.email.AuthEmailViewModel
import ru.poprobuy.poprobuy.ui.auth.name.AuthNameViewModel
import ru.poprobuy.poprobuy.ui.auth.phone.AuthPhoneViewModel
import ru.poprobuy.poprobuy.ui.auth.policy.AuthPolicyViewModel
import ru.poprobuy.poprobuy.ui.home.HomeViewModel
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingViewModel
import ru.poprobuy.poprobuy.ui.profile.ProfileViewModel
import ru.poprobuy.poprobuy.ui.scanner.ScannerViewModel
import ru.poprobuy.poprobuy.ui.splash.SplashViewModel

val screenModule = module {
  viewModel { SplashViewModel(get(), get()) }
  viewModel { OnboardingViewModel(get(), get()) }

  // Auth
  viewModel { AuthPolicyViewModel(get(), get()) }
  viewModel { AuthPhoneViewModel(get()) }
  viewModel { AuthCodeConfirmationViewModel(get()) }
  viewModel { AuthNameViewModel(get()) }
  viewModel { (userName: String) ->
    AuthEmailViewModel(
      userName = userName,
      navigation = get(),
      authRepository = get()
    )
  }

  // Home
  viewModel { HomeViewModel(get()) }
  viewModel { ProfileViewModel(get(), get()) }
  viewModel { ScannerViewModel(get()) }
}
