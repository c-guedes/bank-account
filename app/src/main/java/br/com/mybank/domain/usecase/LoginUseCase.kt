package br.com.mybank.domain.usecase

import br.com.mybank.data.BankApiRepositoryImpl
import br.com.mybank.data.model.LoginRequest
import br.com.mybank.domain.LoginresponseBO
import io.reactivex.Single

class LoginUseCase(
    private val repository: BankApiRepositoryImpl
) : UseCase<LoginresponseBO, LoginUseCase.Params> {

    override fun execute(params: Params): Single<LoginresponseBO> {
        return params.userInfo?.let {
            repository.doLogin(it)
        } ?: Single.error(Throwable("No data set"))
    }

    data class Params(
        val userInfo: LoginRequest?
    )
}