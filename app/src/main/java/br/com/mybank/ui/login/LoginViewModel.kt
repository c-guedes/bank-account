package br.com.mybank.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mybank.BaseViewModel
import br.com.mybank.data.*
import br.com.mybank.data.model.LoginRequest
import br.com.mybank.data.model.UserOnboardData
import br.com.mybank.domain.LoginResponseBO
import br.com.mybank.domain.usecase.HasLoggedInUseCase
import br.com.mybank.domain.usecase.LoginUseCase
import br.com.mybank.domain.usecase.UserDataUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val hasLoggedInUseCase: HasLoggedInUseCase,
    private val userDataUseCase: UserDataUseCase
) : BaseViewModel() {
    val loginState: LiveData<StateResponse<LoginResponseBO>> get() = _loginState
    private val _loginState = MutableLiveData<StateResponse<LoginResponseBO>>()

    fun doLogin(user: String?, password: String?) {
        _loginState.value = StateLoading()
        loginUseCase.execute(
            LoginUseCase.Params(
                LoginRequest(
                    user = user,
                    password = password
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

    fun userHasLoggedIn(): Boolean = hasLoggedInUseCase.userHasLoggedIn()

    fun setLoggedUser() {
        hasLoggedInUseCase.setLoggedUser(true)
    }

    fun setUserData(login: String?, password: String?) {
        userDataUseCase.setUserData(
            UserOnboardData(
                user = login,
                password = password
            )
        )
    }

    fun getUserData(): UserOnboardData =
        userDataUseCase.getUserData()

}