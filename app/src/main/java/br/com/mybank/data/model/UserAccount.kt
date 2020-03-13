package br.com.mybank.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAccount(
    @SerializedName("agency")
    val agency: String = "",
    @SerializedName("balance")
    val balance: Double = 0.0,
    @SerializedName("bankAccount")
    val bankAccount: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("userId")
    val userId: Int = 0
) : Parcelable