package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserUpdateRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.network.NetworkResource
import ru.poprobuy.poprobuy.util.network.apiCall

class UserRepository(
  private val api: PoprobuyApi,
  private val userPreferences: UserPreferences
) {

  suspend fun updateUser(email: String, name: String): NetworkResource<Unit, Any> {
    val request = UserUpdateRequest(email = email, firstName = name)
    return apiCall { api.updateUser(request) }
  }

  suspend fun fetchUser(): NetworkResource<User, Any> = apiCall { api.getUser() }

  fun getUser(): User? = userPreferences.user

  fun saveUser(user: User) {
    userPreferences.user = user
  }

}
