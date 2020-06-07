@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingNavigation
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingNavigationImpl
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingViewModel
import ru.poprobuy.poprobuy.ui.splash.SplashNavigation
import ru.poprobuy.poprobuy.ui.splash.SplashNavigationImpl
import ru.poprobuy.poprobuy.ui.splash.SplashViewModel

val screenModule = module {
  // ViewModel
  viewModel { SplashViewModel(get(), get()) }
  viewModel { OnboardingViewModel(get(), get()) }

  // Navigator
  factory { SplashNavigationImpl() as SplashNavigation }
  factory { OnboardingNavigationImpl() as OnboardingNavigation }
}
