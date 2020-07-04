@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import org.koin.dsl.module
import ru.poprobuy.poprobuy.ui.auth.code.AuthCodeConfirmationNavigation
import ru.poprobuy.poprobuy.ui.auth.code.AuthCodeConfirmationNavigationImpl
import ru.poprobuy.poprobuy.ui.auth.email.AuthEmailNavigation
import ru.poprobuy.poprobuy.ui.auth.email.AuthEmailNavigationImpl
import ru.poprobuy.poprobuy.ui.auth.name.AuthNameNavigation
import ru.poprobuy.poprobuy.ui.auth.name.AuthNameNavigationImpl
import ru.poprobuy.poprobuy.ui.auth.phone.AuthPhoneNavigation
import ru.poprobuy.poprobuy.ui.auth.phone.AuthPhoneNavigationImpl
import ru.poprobuy.poprobuy.ui.auth.policy.AuthPolicyNavigation
import ru.poprobuy.poprobuy.ui.auth.policy.AuthPolicyNavigationImpl
import ru.poprobuy.poprobuy.ui.home.HomeNavigation
import ru.poprobuy.poprobuy.ui.home.HomeNavigationImpl
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingNavigation
import ru.poprobuy.poprobuy.ui.onboarding.OnboardingNavigationImpl
import ru.poprobuy.poprobuy.ui.scanner.ScannerNavigation
import ru.poprobuy.poprobuy.ui.scanner.ScannerNavigationImpl
import ru.poprobuy.poprobuy.ui.splash.SplashNavigation
import ru.poprobuy.poprobuy.ui.splash.SplashNavigationImpl

val navigationModule = module {
  factory { SplashNavigationImpl() as SplashNavigation }
  factory { OnboardingNavigationImpl() as OnboardingNavigation }

  // Auth
  factory { AuthPolicyNavigationImpl() as AuthPolicyNavigation }
  factory { AuthPhoneNavigationImpl() as AuthPhoneNavigation }
  factory { AuthCodeConfirmationNavigationImpl() as AuthCodeConfirmationNavigation }
  factory { AuthNameNavigationImpl() as AuthNameNavigation }
  factory { AuthEmailNavigationImpl() as AuthEmailNavigation }

  // Home
  factory { HomeNavigationImpl() as HomeNavigation }
  factory { ScannerNavigationImpl() as ScannerNavigation }
}
