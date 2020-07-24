package ru.poprobuy.poprobuy.ui.products.select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.extension.asLiveData

class ProductSelectionViewModel : BaseViewModel() {

  private val _stateLive = MutableLiveData<ProductSelectionState>()
  val stateLive = _stateLive.asLiveData()

  private var product: ProductUiModel? = null

  fun setProduct(product: ProductUiModel) {
    this.product = product
    _stateLive.postValue(ProductSelectionState.Product(product))
  }

  fun confirmSelection() {
    val productCopy = product
    if (productCopy == null) {
      // TODO: 11.07.2020 Show error
      return
    }

    viewModelScope.launch {
      _stateLive.postValue(ProductSelectionState.Product(productCopy, isLoading = true))
      delay(1500)
      _stateLive.postValue(ProductSelectionState.Success)
      delay(1500)
      _stateLive.postValue(ProductSelectionState.Error)
    }
  }

}
