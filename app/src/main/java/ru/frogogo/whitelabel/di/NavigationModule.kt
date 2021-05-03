@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import org.koin.dsl.module
import ru.frogogo.whitelabel.ui.MainNavigation
import ru.frogogo.whitelabel.ui.MainNavigationImpl
import ru.frogogo.whitelabel.ui.auth.code.AuthCodeConfirmationNavigation
import ru.frogogo.whitelabel.ui.auth.code.AuthCodeConfirmationNavigationImpl
import ru.frogogo.whitelabel.ui.auth.email.AuthEmailNavigation
import ru.frogogo.whitelabel.ui.auth.email.AuthEmailNavigationImpl
import ru.frogogo.whitelabel.ui.auth.name.AuthNameNavigation
import ru.frogogo.whitelabel.ui.auth.name.AuthNameNavigationImpl
import ru.frogogo.whitelabel.ui.auth.phone.AuthPhoneNavigation
import ru.frogogo.whitelabel.ui.auth.phone.AuthPhoneNavigationImpl
import ru.frogogo.whitelabel.ui.auth.policy.AuthPolicyNavigation
import ru.frogogo.whitelabel.ui.auth.policy.AuthPolicyNavigationImpl
import ru.frogogo.whitelabel.ui.onboarding.OnboardingNavigation
import ru.frogogo.whitelabel.ui.onboarding.OnboardingNavigationImpl
import ru.frogogo.whitelabel.ui.profile.ProfileNavigation
import ru.frogogo.whitelabel.ui.profile.ProfileNavigationImpl
import ru.frogogo.whitelabel.ui.profile.receipts.ReceiptsNavigation
import ru.frogogo.whitelabel.ui.profile.receipts.ReceiptsNavigationImpl
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsNavigation
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsNavigationImpl
import ru.frogogo.whitelabel.ui.splash.SplashNavigation
import ru.frogogo.whitelabel.ui.splash.SplashNavigationImpl

val navigationModule = module {
  factory { MainNavigationImpl() as MainNavigation }
  factory { SplashNavigationImpl() as SplashNavigation }
  factory { OnboardingNavigationImpl() as OnboardingNavigation }

  // Auth
  factory { AuthPolicyNavigationImpl() as AuthPolicyNavigation }
  factory { AuthPhoneNavigationImpl() as AuthPhoneNavigation }
  factory { AuthCodeConfirmationNavigationImpl() as AuthCodeConfirmationNavigation }
  factory { AuthNameNavigationImpl() as AuthNameNavigation }
  factory { AuthEmailNavigationImpl() as AuthEmailNavigation }

  // Profile
  factory { ProfileNavigationImpl() as ProfileNavigation }
  factory { ReceiptsNavigationImpl() as ReceiptsNavigation }
  factory { ReceiptDetailsNavigationImpl() as ReceiptDetailsNavigation }
}
