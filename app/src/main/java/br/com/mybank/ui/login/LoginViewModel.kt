package br.com.mybank.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mybank.BaseViewModel
import br.com.mybank.data.*
import br.com.mybank.data.model.LoginRequest
import br.com.mybank.domain.LoginresponseBO
import br.com.mybank.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    val loginState: LiveData<StateResponse<LoginresponseBO>> get() = _loginState
    private val _loginState = MutableLiveData<StateResponse<LoginresponseBO>>()

    fun doLogin() {
        _loginState.value = StateLoading()
        loginUseCase.execute(
            LoginUseCase.Params(
                LoginRequest(
                    user = "test_user",
                    password = "Test@1"
                )
            )
        ).subscribe(
            { response ->
                if (response.backendError?.code != null) {
                    response.backendError.let { e ->
                        _loginState.value = StateBusinessError(e)
                    }
                } else {
                    _loginState.value = StateSuccess(response)
                }
            },
            { e -> _loginState.value = StateError(e) }
        ).let {
            disposables.add(it)
        }
    }
}