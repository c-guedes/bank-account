package br.com.mybank.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentsListResponse(
    @SerializedName("error")
    val backendError: BackendError?,
    @SerializedName("statementList")
    val statementList: List<Statement?>
) : Parcelable