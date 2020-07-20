package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.auth.UserUpdateRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.network.NetworkResource
import ru.poprobuy.poprobuy.util.network.apiCall

class UserRepository(private val api: PoprobuyApi) {

  suspend fun updateUser(email: String, name: String): NetworkResource<Unit, Any> {
    val request = UserUpdateRequest(email = email, firstName = name)
    return apiCall { api.updateUser(request) }
  }

}
