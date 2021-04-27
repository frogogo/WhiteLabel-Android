package ru.frogogo.whitelabel.core.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<out T : BaseViewModel>(
  @LayoutRes private val layoutId: Int,
) : AppCompatActivity() {

  protected abstract val viewModel: T

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)

    initViews()
    initObservers()

    viewModel.onCreate()
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()
  }

  override fun onStop() {
    super.onStop()
    viewModel.onStop()
  }

  protected open fun initViews(): Unit = Unit

  protected open fun initObservers(): Unit = Unit
}
