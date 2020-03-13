package br.com.mybank.domain

import android.os.Parcelable
import br.com.mybank.data.model.BackendError
import br.com.mybank.data.model.Statement
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentsResponseBO(
    val backendError: BackendError?,
    val statementList: List<Statement?>
) : Parcelable