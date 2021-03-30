package ru.poprobuy.poprobuy.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.poprobuy.poprobuy.R

class ColoredSwipeRefreshLayout : SwipeRefreshLayout {

  init {
    setColorSchemeResources(
      R.color.swipe_refresh_1,
      R.color.swipe_refresh_2
    )
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context) : super(context)
}
