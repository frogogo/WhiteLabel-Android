package ru.frogogo.whitelabel.ui.profile.coupons.delegate

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponImageUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.dictionary.CouponCodeType
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponsDataLoadDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val stateHandlerDelegate: CouponsStateHandlerDelegate,
) : BaseViewModelDelegate(dispatchersProvider),
  CouponsDataLoadDelegate {

  override fun refreshData() {
    scope.launch {
      stateHandlerDelegate.showLoader()

      // TODO: 03.05.2021 Real data
      delay(1500)
      stateHandlerDelegate.showData(
        listOf(
          CouponUiModel(
            id = 0,
            name = "Ножеточка Lion Sabatier",
            description = "",
            image = CouponImageUiModel(
              largeUrl = "",
              thumbUrl = "https://picsum.photos/200"
            ),
            code = CouponCodeUiModel(
              value = "13123",
              type = CouponCodeType.CODE_128
            )
          ),
          CouponUiModel(
            id = 1,
            name = "Ножеточка Lion Sabatier",
            description = "Курс на социально-ориентированный национальный проект способствует подготовке и реализации прогресса профессионального сообщества. Задача организации, в особенности же разбавленное изрядной долей эмпатии, рациональное мышление в значительной степени обусловливает важность укрепления моральных ценностей. В своём стремлении улучшить пользовательский опыт мы упускаем, что интерактивные прототипы лишь добавляют фракционных разногласий и описаны максимально подробно. Для современного мира перспективное планирование предопределяет высокую востребованность кластеризации усилий. С учётом сложившейся международной обстановки, новая модель организационной деятельности предоставляет широкие возможности для направлений прогрессивного развития. В своём стремлении улучшить пользовательский опыт мы упускаем, что элементы политического процесса будут рассмотрены исключительно в разрезе маркетинговых и финансовых предпосылок. Принимая во внимание показатели успешности, разбавленное изрядной долей эмпатии, рациональное мышление является качественно новой ступенью существующих финансовых и административных условий. Разнообразный и богатый опыт говорит нам, что внедрение современных методик позволяет оценить значение новых предложений. Не следует, однако, забывать, что высококачественный прототип будущего проекта выявляет срочную потребность как самодостаточных, так и внешне зависимых концептуальных решений. Банальные, но неопровержимые выводы, а также сторонники тоталитаризма в науке лишь добавляют фракционных разногласий и в равной степени предоставлены сами себе.",
            image = CouponImageUiModel(
              largeUrl = "https://picsum.photos/1000/500",
              thumbUrl = "https://picsum.photos/200"
            ),
            code = CouponCodeUiModel(
              value = "barcode value",
              type = CouponCodeType.QR
            )
          )
        )
      )
    }
  }
}
