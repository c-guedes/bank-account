package br.com.mybank.domain.usecase

import br.com.mybank.data.BankApiRepositoryImpl
import br.com.mybank.domain.PaymentsResponseBO
import io.reactivex.Single

class PaymentsUseCase(
    private val repository: BankApiRepositoryImpl
) : UseCase<PaymentsResponseBO, PaymentsUseCase.Params> {

    override fun execute(params: Params): Single<PaymentsResponseBO> {
        return repository.getPayments(params.idUser)
    }

    data class Params(
        val idUser: Int?
    )
}