package ru.frogogo.whitelabel.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.frogogo.whitelabel.R

class ColoredSwipeRefreshLayout : SwipeRefreshLayout {

  init {
    setColorSchemeResources(
      R.color.swipe_refresh_1,
      R.color.swipe_refresh_2,
    )
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context) : super(context)
}
