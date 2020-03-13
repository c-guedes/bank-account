package br.com.mybank.data.model


import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("error")
    val backendError: BackendError?,
    @SerializedName("userAccount")
    val userAccount: UserAccount?
)