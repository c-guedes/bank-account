package br.com.mybank.domain

import android.os.Parcelable
import br.com.mybank.data.model.BackendError
import br.com.mybank.data.model.UserAccount
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginresponseBO(
    val backendError: BackendError?,
    val userAccount: UserAccount?
) : Parcelable