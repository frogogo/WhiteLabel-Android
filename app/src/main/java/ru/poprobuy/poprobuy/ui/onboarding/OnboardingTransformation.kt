package ru.poprobuy.poprobuy.ui.onboarding

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import ru.poprobuy.poprobuy.R

class OnboardingTransformation : ViewPager2.PageTransformer {

  override fun transformPage(page: View, position: Float) {
    val icon = page.findViewById<View>(R.id.image_view_icon)
    val title = page.findViewById<View>(R.id.text_view_title)

    if (position <= 1 && position >= -1) {
      icon.translationX = position * (page.width / 3f)
      title.translationX = -position * (page.width / 4f)
    }
  }

}
