package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserUpdateRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class UserRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: PoprobuyApi,
  private val userPreferences: UserPreferences,
) : Repository(dispatcher), UserRepository {

  override suspend fun updateUser(email: String, name: String): Result<Unit, NetworkError<ErrorResponse>> =
    withIOContext {
      val request = UserUpdateRequest(email = email, firstName = name)
      apiCall { api.updateUser(request) }.mapToResult()
    }

  override suspend fun fetchUser(): Result<User, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getUser() }.mapToResult()
  }

  override fun getUser(): User? = userPreferences.user

  override fun saveUser(user: User) {
    userPreferences.user = user
  }

}
