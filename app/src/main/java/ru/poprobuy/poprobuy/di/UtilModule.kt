package ru.poprobuy.poprobuy.di

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.core.navigation.NavigationRouter
import ru.poprobuy.poprobuy.ui.products.select.MachineProductSelectionInteractor
import ru.poprobuy.poprobuy.util.OtpRequestDisabler
import ru.poprobuy.poprobuy.util.ProfileUtils
import ru.poprobuy.poprobuy.util.ResourceProvider
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager

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
}
