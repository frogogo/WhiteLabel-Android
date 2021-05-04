package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponImageUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeCouponProgressUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.dictionary.CouponCodeType
import ru.frogogo.whitelabel.dictionary.ReceiptState
import java.util.*

fun HomeResponse.toDomain(): HomeState =
  HomeState.Progress(
    couponProgress = HomeCouponProgressUiModel(
      progressCurrent = 25,
      progressTarget = 500,
      notice = "1231 123"
    ),
    coupons = listOf(
      CouponUiModel(
        id = 0,
        name = "Ножеточка Lion Sabatier",
        description = "",
        image = CouponImageUiModel(
          largeUrl = "",
          thumbUrl = "https://picsum.photos/200"
        ),
        code = CouponCodeUiModel(
          value = "QR code value",
          type = CouponCodeType.QR
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
          value = "1234567890",
          type = CouponCodeType.CODE_128
        )
      )
    ),
    receipts = listOf(
      ReceiptUiModel(
        id = 1,
        number = 0,
        date = Date(),
        value = 0,
        state = ReceiptState.APPROVED,
        rejectReason = null,
      ),
      ReceiptUiModel(
        id = 1,
        number = 0,
        date = Date(),
        value = 0,
        state = ReceiptState.PROCESSING,
        rejectReason = null,
      ),
      ReceiptUiModel(
        id = 1,
        number = 0,
        date = Date(),
        value = 0,
        state = ReceiptState.REJECTED,
        rejectReason = null,
      ),
    )
  )
