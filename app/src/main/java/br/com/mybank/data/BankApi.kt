package br.com.mybank.data

import br.com.mybank.data.model.LoginResponse
import br.com.mybank.data.model.PaymentsListResponse
import io.reactivex.Single
import retrofit2.http.*


interface BankApi {
    @FormUrlEncoded
    @POST("login")
    fun doLogin(@Field("user") user: String?, @Field("password") password: String?): Single<LoginResponse>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("statements/{idUser}")
    fun getStatements(@Path("idUser") idUser: Int?): Single<PaymentsListResponse>
}