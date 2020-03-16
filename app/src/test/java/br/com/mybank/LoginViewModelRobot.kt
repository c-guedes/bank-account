package br.com.mybank

import androidx.lifecycle.Observer
import br.com.mybank.data.SessionUtil
import br.com.mybank.data.StateResponse
import br.com.mybank.data.StateSuccess
import br.com.mybank.data.model.BackendError
import br.com.mybank.data.model.UserAccount
import br.com.mybank.data.model.UserOnboardData
import br.com.mybank.domain.LoginResponseBO
import br.com.mybank.domain.usecase.HasLoggedInUseCase
import br.com.mybank.domain.usecase.LoginUseCase
import br.com.mybank.domain.usecase.UserDataUseCase
import br.com.mybank.ui.login.LoginViewModel
import io.mockk.every
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat

object LoginViewModelRobot {
    val sessionUtil = SessionUtil.client

    class LoginViewModelRobotArrange {
        fun mockHasCredentials(
            hasLoggedInUseCase: HasLoggedInUseCase
        ) {
            every {
                hasLoggedInUseCase.userHasLoggedIn()
            } returns true
        }

        fun mockHasNotCredentials(
            hasLoggedInUseCase: HasLoggedInUseCase
        ) {
            every {
                hasLoggedInUseCase.userHasLoggedIn()
            } returns false
        }

        private fun mockData(): UserOnboardData =
            UserOnboardData(
                user = "caique-guedes@hotmail.com",
                password = "Ca1qu&"
            )

        private fun mockUserAccount(): UserAccount =
            UserAccount(
                agency = "313",
                balance = 1800.0,
                bankAccount = "3232/55",
                name = "Caique Guedes de Almeida e Silva",
                userId = 0
            )

        fun mockUserData(
            userDataUseCase: UserDataUseCase
        ) {
            every {
                userDataUseCase.getUserData()
            } returns mockData()
        }

        fun mockLoginSucesfull(
            loginUseCase: LoginUseCase,
            loginResponseBO: LoginResponseBO? = null
        ) {
            every {
                loginUseCase.execute(any())
            } returns Single.just(
                loginResponseBO ?: LoginResponseBO(
                    BackendError(null, null), mockUserAccount()
                )
            )
        }

    }

    class LoginViewModelRobotAct {
        fun doLogin(
            subject: LoginViewModel,
            observer: Observer<StateResponse<LoginResponseBO>>
        ) {
            subject.loginState.observeForever(observer)
            subject.doLogin(user = "caique-guedes@hotmail.com", password = "Ca1qu&")
            subject.loginState.removeObserver(observer)
        }

        fun setUserData(
            subject: LoginViewModel
        ) {
            subject.setUserData(login = "caique-guedes@hotmail.com", password = "Ca1qu&")
        }

        fun setHasCredentials(
            subject: LoginViewModel
        ) {
            subject.setLoggedUser()
        }
    }

    class LoginViewModelRobotAssert {
        fun isStateSucess(subject: LoginViewModel) {
            subject.loginState.value.assertInstanceOf(StateSuccess::class.java)
        }

        fun isFirstTime(subject: LoginViewModel) {
            assertEquals(false, subject.userHasLoggedIn())
        }

        fun isDataSetted(subject: LoginViewModel) {
            assertEquals("caique-guedes@hotmail.com", subject.getUserData().user)
        }

        fun isNotFirstTimeLogged(subject: LoginViewModel) {
            assertEquals(true, subject.userHasLoggedIn())
        }
    }

    fun arrange(func: LoginViewModelRobotArrange.() -> Unit) =
        LoginViewModelRobotArrange().apply(func)

    fun act(func: LoginViewModelRobotAct.() -> Unit) =
        LoginViewModelRobotAct().apply(func)

    fun assert(func: LoginViewModelRobotAssert.() -> Unit) =
        LoginViewModelRobotAssert().apply(func)

    infix fun <T> T.assertInstanceOf(clazz: Class<*>) {
        assertThat(this, instanceOf(clazz))
    }
}