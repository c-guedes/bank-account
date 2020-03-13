package br.com.mybank.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Statement(
    @SerializedName("date")
    val date: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("value")
    val value: Double?
) : Parcelable