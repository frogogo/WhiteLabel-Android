package ru.frogogo.whitelabel.data.model.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem

private const val ID = "ITEM_PAGE"

data class OnboardingPage(
  @StringRes val titleResId: Int,
  @StringRes val descriptionResId: Int,
  @DrawableRes val iconResId: Int,
) : RecyclerViewItem {

  override fun getId() = "$ID${hashCode()}"
}
