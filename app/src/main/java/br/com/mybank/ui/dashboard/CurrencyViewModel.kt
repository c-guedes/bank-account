package br.com.mybank.ui.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mybank.BaseViewModel
import br.com.mybank.data.*
import br.com.mybank.domain.PaymentsResponseBO
import br.com.mybank.domain.usecase.PaymentsUseCase
import br.com.mybank.domain.usecase.UserDataUseCase

class CurrencyViewModel(
    private val paymentsUseCase: PaymentsUseCase,
    private val userDataUseCase: UserDataUseCase
) : BaseViewModel() {
    val paymentsResponse: LiveData<StateResponse<PaymentsResponseBO>> get() = _paymentsResponse
    private val _paymentsResponse = MutableLiveData<StateResponse<PaymentsResponseBO>>()

    @SuppressLint("CheckResult")
    fun getPaymentsList() {
        _paymentsResponse.value = StateLoading()
        paymentsUseCase.execute(
            PaymentsUseCase.Params(
                idUser = SessionUtil.client?.userId
            )
        ).subscribe(
            { response -> _paymentsResponse.value = StateSuccess(response) },
            { e -> _paymentsResponse.value = StateError(e) }
        ).let {
            disposables.add(it)
        }
    }

    fun clearUserData() {
        userDataUseCase.clearUserData()
    }
}