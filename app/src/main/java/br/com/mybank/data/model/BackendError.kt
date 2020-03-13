package br.com.mybank.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class BackendError(
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?
) : Parcelable