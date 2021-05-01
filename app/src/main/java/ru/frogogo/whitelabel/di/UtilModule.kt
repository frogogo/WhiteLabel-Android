@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.frogogo.whitelabel.core.navigation.NavigationRouter
import ru.frogogo.whitelabel.ui.products.select.MachineProductSelectionInteractor
import ru.frogogo.whitelabel.util.OtpRequestDisabler
import ru.frogogo.whitelabel.util.ProfileUtils
import ru.frogogo.whitelabel.util.ResourceProvider
import ru.frogogo.whitelabel.util.analytics.AnalyticsManager
import ru.frogogo.whitelabel.util.dispatcher.AppDispatchers
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

val utilModule = module {
  single { AnalyticsManager(FirebaseAnalytics.getInstance(androidContext())) }
  single { ResourceProvider(androidContext()) }
  single { ProfileUtils(androidContext()) }
  single { MachineProductSelectionInteractor() }
  factory { (navController: NavController) ->
    NavigationRouter(
      controller = navController
    )
  }
  factory { OtpRequestDisabler() }
  single { RecyclerView.RecycledViewPool() }
  single { AppDispatchers as DispatchersProvider }
}
