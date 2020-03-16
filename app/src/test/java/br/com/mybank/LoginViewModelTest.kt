package br.com.mybank

import android.os.Build
import androidx.lifecycle.Observer
import br.com.mybank.LoginViewModelRobot.act
import br.com.mybank.LoginViewModelRobot.arrange
import br.com.mybank.LoginViewModelRobot.assert
import br.com.mybank.data.StateResponse
import br.com.mybank.domain.LoginResponseBO
import br.com.mybank.domain.usecase.HasLoggedInUseCase
import br.com.mybank.domain.usecase.LoginUseCase
import br.com.mybank.domain.usecase.UserDataUseCase
import br.com.mybank.ui.login.LoginViewModel
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LoginViewModelTest {
    private val loginUseCase = mockk<LoginUseCase>(relaxed = true)
    private val hasLoginUseCase = mockk<HasLoggedInUseCase>(relaxed = true)
    private val userDataUseCase = mockk<UserDataUseCase>(relaxed = true)

    private val observer = mockk<Observer<StateResponse<LoginResponseBO>>>(relaxed = true)

    private val subject = LoginViewModel(
        loginUseCase, hasLoginUseCase, userDataUseCase
    )

    @Test
    fun `when user has credentials stored should auto login`() {
        arrange {
            mockHasCredentials(hasLoginUseCase)
            mockLoginSucesfull(loginUseCase)
        }
        act {
            doLogin(subject, observer)
        }
        assert {
            isStateSucess(subject)
        }
    }

    @Test
    fun `if is first time using app should require login`() {
        arrange {
            mockHasNotCredentials(hasLoginUseCase)
        }
        assert {
            isFirstTime(subject)
        }
    }

    @Test
    fun `when login is success should set user data`() {
        arrange {
            mockLoginSucesfull(loginUseCase)
            mockUserData(userDataUseCase)
        }
        act {
            doLogin(subject, observer)
            setUserData(subject)
        }
        assert {
            isDataSetted(subject)
        }
    }

    @Test
    fun `when user has not credentials should set to true`() {
        arrange {
            mockHasCredentials(hasLoginUseCase)
        }
        act {
            setHasCredentials(subject)
        }
        assert {
            isNotFirstTimeLogged(subject)
        }
    }

}