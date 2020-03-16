package br.com.mybank.domain

import android.os.Parcelable
import br.com.mybank.data.model.BackendError
import br.com.mybank.data.model.UserAccount
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponseBO(
    val backendError: BackendError?,
    val userAccount: UserAccount?
) : Parcelable