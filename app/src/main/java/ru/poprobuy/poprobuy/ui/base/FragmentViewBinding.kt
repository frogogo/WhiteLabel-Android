package ru.poprobuy.poprobuy.ui.base

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// https://github.com/kirich1409/ViewBindingPropertyDelegate

class FragmentViewBinder<T : ViewBinding>(private val viewBindingClass: Class<T>) {

  /**
   * Cache static method `ViewBinding.bind(View)`
   */
  private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
    viewBindingClass.getMethod("bind", View::class.java)
  }

  /**
   * Create new [ViewBinding] instance
   */
  @Suppress("UNCHECKED_CAST")
  fun bind(fragment: Fragment): T {
    return bindViewMethod(null, fragment.requireView()) as T
  }
}

abstract class ViewBindingProperty<R, T : ViewBinding>(
  private val viewBinder: (R) -> T
) : ReadOnlyProperty<R, T> {

  internal var viewBinding: T? = null
  private val lifecycleObserver = BindingLifecycleObserver()

  protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

  @MainThread
  override fun getValue(thisRef: R, property: KProperty<*>): T {
    check(Looper.myLooper() == Looper.getMainLooper())
    viewBinding?.let { return it }

    getLifecycleOwner(thisRef).lifecycle.addObserver(lifecycleObserver)
    return viewBinder(thisRef).also { viewBinding = it }
  }

  private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

    private val mainHandler = Handler(Looper.getMainLooper())

    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
      owner.lifecycle.removeObserver(this)
      mainHandler.post {
        viewBinding = null
      }
    }
  }
}

class FragmentViewBindingProperty<T : ViewBinding>(
  viewBinder: (Fragment) -> T
) : ViewBindingProperty<Fragment, T>(viewBinder) {
  override fun getLifecycleOwner(thisRef: Fragment) = thisRef.viewLifecycleOwner
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this]
 */
@Suppress("unused")
inline fun <reified T : ViewBinding> Fragment.viewBinding(
  noinline viewBinder: (Fragment) -> T = FragmentViewBinder(T::class.java)::bind
): ReadOnlyProperty<Fragment, T> {
  return FragmentViewBindingProperty(viewBinder)
}
