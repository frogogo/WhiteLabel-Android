package ru.frogogo.whitelabel.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import ru.frogogo.whitelabel.data.model.api.auth.*
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
}
