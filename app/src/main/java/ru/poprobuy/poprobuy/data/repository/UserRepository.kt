package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserUpdateRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class UserRepository(
  private val api: PoprobuyApi,
  private val userPreferences: UserPreferences,
) {

  suspend fun updateUser(email: String, name: String): Result<Unit, NetworkError<ErrorResponse>> {
    val request = UserUpdateRequest(email = email, firstName = name)
    return apiCall { api.updateUser(request) }.mapToResult()
  }

  suspend fun fetchUser(): Result<User, NetworkError<ErrorResponse>> =
    apiCall { api.getUser() }.mapToResult()

  fun getUser(): User? = userPreferences.user

  fun saveUser(user: User) {
    userPreferences.user = user
  }

}
