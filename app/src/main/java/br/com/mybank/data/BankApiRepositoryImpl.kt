package br.com.mybank.data

import android.content.Context
import br.com.mybank.api.doRequest
import br.com.mybank.data.model.LoginRequest
import br.com.mybank.domain.LoginResponseBO
import br.com.mybank.domain.PaymentsResponseBO
import io.reactivex.Single

class BankApiRepositoryImpl(
    private val api: BankApi,
    private val context: Context
) {
    fun doLogin(request: LoginRequest): Single<LoginResponseBO> {
        return doRequest {
            api.doLogin(user = request.user, password = request.password)
        }.map { response ->
            LoginResponseBO(
                backendError = response.backendError,
                userAccount = response.userAccount
            )
        }
    }

    fun getPayments(idUser: Int?): Single<PaymentsResponseBO> {
        return doRequest {
            api.getStatements(
                idUser = idUser
            )
        }.map { response ->
            PaymentsResponseBO(
                backendError = response.backendError,
                statementList = response.statementList
            )
        }
    }
}