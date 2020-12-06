package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.onboarding.OnboardingPage
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider

class OnboardingRepositoryImpl(
  dispatchers: DispatchersProvider,
  private val userPreferences: UserPreferences,
) : Repository(dispatchers), OnboardingRepository {

  override fun getPages(): List<OnboardingPage> = listOf(
    OnboardingPage(
      titleResId = R.string.onboarding_page_1_title,
      descriptionResId = R.string.onboarding_page_1_description,
      iconResId = R.drawable.onboarding_icon_1
    ),
    OnboardingPage(
      titleResId = R.string.onboarding_page_2_title,
      descriptionResId = R.string.onboarding_page_2_description,
      iconResId = R.drawable.onboarding_icon_2
    ),
    OnboardingPage(
      titleResId = R.string.onboarding_page_3_title,
      descriptionResId = R.string.onboarding_page_3_description,
      iconResId = R.drawable.onboarding_icon_3
    )
  )

  override fun setOnboardingCompleted() {
    userPreferences.onboardingCompleted = true
  }

}
