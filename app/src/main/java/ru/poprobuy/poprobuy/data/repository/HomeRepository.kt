package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.network.NetworkResource
import ru.poprobuy.poprobuy.util.network.apiCall

class HomeRepository(
  private val api: PoprobuyApi
) {

  suspend fun getHome(): NetworkResource<HomeResponse, Any> = apiCall { api.getHome() }

}
