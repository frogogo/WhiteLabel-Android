package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.data.model.ui.onboarding.OnboardingPage

interface OnboardingRepository {

  fun getPages(): List<OnboardingPage>

  fun setOnboardingCompleted()
}
