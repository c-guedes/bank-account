package br.com.mybank

import android.os.Build
import br.com.mybank.data.BankApiRepositoryImpl
import br.com.mybank.data.model.BackendError
import br.com.mybank.data.model.LoginRequest
import br.com.mybank.data.model.UserAccount
import br.com.mybank.domain.LoginResponseBO
import br.com.mybank.domain.usecase.LoginUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LoginUseCaseTest {
    private val repository = mockk<BankApiRepositoryImpl>(relaxed = true)

    private val useCase = LoginUseCase(repository)

    @Test
    fun execute() {
        every {
            useCase.execute(
                params = LoginUseCase.Params(
                    LoginRequest(
                        user = "caique-guedes@hotmail.com",
                        password = "Ca1qu&"
                    )
                )
            )
        } returns Single.just(
            LoginResponseBO(
                backendError = BackendError(null, null),
                userAccount = UserAccount(
                    agency = "313",
                    balance = 1800.0,
                    bankAccount = "3232/55",
                    name = "Caique Guedes de Almeida e Silva",
                    userId = 0
                )
            )
        )

        val blockToTest = useCase.execute(
            params = LoginUseCase.Params(
                LoginRequest(
                    user = "caique-guedes@hotmail.com",
                    password = "Ca1qu&"
                )
            )
        ).blockingGet()

        val blockToBeTestted = useCase.execute(
            params = LoginUseCase.Params(
                LoginRequest(
                    user = "caique-guedes@hotmail.com",
                    password = "Ca1qu&"
                )
            )
        ).blockingGet()

        Assert.assertEquals(blockToTest, blockToBeTestted)
    }

    @Test
    fun executeWithError() {
        var e: Throwable? = null

        every {
            useCase.execute(
                params = LoginUseCase.Params(
                    LoginRequest(
                        user = "caique-guedes@hotmail.com",
                        password = "Ca1qu&"
                    )
                )
            )
        } returns Single.error(Throwable("No data set"))

        try {
            useCase.execute(
                params = LoginUseCase.Params(
                    LoginRequest(
                        user = "caique-guedes@hotmail.com",
                        password = "Ca1qu&"
                    )
                )
            ).blockingGet()
        } catch (ex: Throwable) {
            e = ex
        }

        e.assertInstanceOf(RuntimeException(Throwable("No data set"))::class.java)
    }

    private infix fun <T> T.assertInstanceOf(clazz: Class<*>) {
        Assert.assertThat(this, CoreMatchers.instanceOf(clazz))
    }
}