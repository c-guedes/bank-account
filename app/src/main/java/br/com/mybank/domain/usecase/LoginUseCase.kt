package br.com.mybank.domain.usecase

import br.com.mybank.data.BankApiRepositoryImpl
import br.com.mybank.data.model.LoginRequest
import br.com.mybank.domain.LoginResponseBO
import io.reactivex.Single

class LoginUseCase(
    private val repository: BankApiRepositoryImpl
) : UseCase<LoginResponseBO, LoginUseCase.Params> {

    override fun execute(params: Params): Single<LoginResponseBO> {
        return params.userInfo?.let {
            repository.doLogin(it)
        } ?: Single.error(Throwable("No data set"))
    }

    data class Params(
        val userInfo: LoginRequest?
    )
}