package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.model.api.user.UserUpdateRequest
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.whitelabel.data.preferences.UserPreferences
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.apiCall
import ru.frogogo.whitelabel.util.network.mapToResult

class UserRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: FrogogoApi,
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
