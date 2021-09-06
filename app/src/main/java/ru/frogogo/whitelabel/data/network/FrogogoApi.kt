package ru.frogogo.whitelabel.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import ru.frogogo.whitelabel.data.model.api.item.Item
import ru.frogogo.whitelabel.data.model.api.item.ItemInfo
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationRequest
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponse
import ru.frogogo.whitelabel.data.model.api.auth.ConfirmationCodeRequest
import ru.frogogo.whitelabel.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.frogogo.whitelabel.data.model.api.auth.TokenRefreshRequest
import ru.frogogo.whitelabel.data.model.api.home.HomeResponse
import ru.frogogo.whitelabel.data.model.api.receipt.Receipt
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptCreationRequest
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.model.api.user.UserUpdateRequest

interface FrogogoApi {

  @POST("user")
  suspend fun requestPasswordCode(@Body body: ConfirmationCodeRequest): Response<ConfirmationCodeRequestResponse>

  @POST("user_token")
  suspend fun authenticate(@Body body: AuthenticationRequest): Response<AuthenticationResponse>

  @POST("user_token")
  suspend fun refreshToken(@Body body: TokenRefreshRequest): Response<AuthenticationResponse>

  @PATCH("user")
  suspend fun updateUser(@Body body: UserUpdateRequest): Response<Unit>

  @GET("user")
  suspend fun getUser(): Response<User>

  @GET("receipts")
  suspend fun getReceipts(): Response<List<Receipt>>

  /**
   * Request for activating qr string from receipt
   */
  @POST("receipts")
  suspend fun createReceipt(@Body body: ReceiptCreationRequest): Response<Receipt>

  @GET("home")
  suspend fun getHome(): Response<HomeResponse>

  @GET("items")
  suspend fun getItems(): Response<List<Item>>

  @GET("items/{id}")
  suspend fun getItem(@Path("id") id: Int): Response<ItemInfo>
}
