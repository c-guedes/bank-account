package br.com.mybank.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginRequest(
    @SerializedName("user") val user: String?,
    @SerializedName("password") val password: String?
) : Parcelable