package ru.frogogo.whitelabel.ui.coupon_info

import android.os.Bundle
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.navigation.fragment.navArgs
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.databinding.FragmentCouponInfoBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.unloadBindingModuleOnClose
import ru.frogogo.whitelabel.ui.coupon_info.code_dialog.CouponCodeDialog
import ru.frogogo.whitelabel.ui.coupon_info.code_dialog.CouponCodeDialog.Companion.showIn
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

private typealias Binding = FragmentCouponInfoBinding

class CouponInfoFragment : BaseFragment<CouponInfoViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: CouponInfoViewModel by viewModel { parametersOf(args.coupon) }

  private val binding: Binding by viewBinding(Binding::bind)
  private val args: CouponInfoFragmentArgs by navArgs()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_coupon_info,
    screen = AnalyticsScreen.COUPON_INFO,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    scope.unloadBindingModuleOnClose()
  }

  override fun initViews() {
    super.initViews()
    binding.apply {
      buttonClose.setOnSafeClickListener(viewModel::onBackButtonClicked)
      buttonCode.setOnSafeClickListener(viewModel::onShowCodeClicked)
    }
  }

  override fun initObservers() {
    super.initObservers()
    viewModel.apply {
      observe(contentLive, ::renderContent)
      observe(effectLiveEvent, ::handleEffect)
    }
  }

  private fun renderContent(coupon: CouponUiModel) {
    binding.apply {
      imageViewCoupon.load(coupon.image.largeUrl)
      textViewCouponId.text = getString(R.string.coupon_number, coupon.id)
      textViewName.text = coupon.name
      TextViewCompat.setPrecomputedText(
        textViewInstruction,
        PrecomputedTextCompat.create(coupon.description, TextViewCompat.getTextMetricsParams(textViewInstruction))
      )
    }
  }

  private fun handleEffect(effect: CouponInfoEffect) {
    @Exhaustive
    when (effect) {
      is CouponInfoEffect.ShowCode -> showCodeDialog(effect.code)
    }
  }

  private fun showCodeDialog(code: CouponCodeUiModel) {
    CouponCodeDialog.newInstance(code).showIn(childFragmentManager)
  }
}
