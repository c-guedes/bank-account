package br.com.mybank

import android.os.Build
import androidx.lifecycle.Observer
import br.com.mybank.CurrencyViewModelRobot.act
import br.com.mybank.CurrencyViewModelRobot.arrange
import br.com.mybank.CurrencyViewModelRobot.assert
import br.com.mybank.data.SessionUtil
import br.com.mybank.data.SessionUtil.client
import br.com.mybank.data.StateResponse
import br.com.mybank.data.model.UserAccount
import br.com.mybank.domain.PaymentsResponseBO
import br.com.mybank.domain.usecase.PaymentsUseCase
import br.com.mybank.domain.usecase.UserDataUseCase
import br.com.mybank.ui.dashboard.CurrencyViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class CurrencyViewModelTest {
    private val paymentsUseCase = mockk<PaymentsUseCase>()
    private val userDataUseCase = mockk<UserDataUseCase>(relaxUnitFun = true)
    private val observer = mockk<Observer<StateResponse<PaymentsResponseBO>>>(relaxed = true)
    private val subject = CurrencyViewModel(
        paymentsUseCase, userDataUseCase
    )

    @Before
    fun setUp() {
        mockkObject(SessionUtil)
        every {
            client
        } returns mockk {
            UserAccount(
                agency = "313",
                balance = 1800.0,
                bankAccount = "3232/55",
                name = "Caique Guedes de Almeida e Silva",
                userId = 0
            )
        }
    }

    @After
    fun tearDown() {
        unmockkObject(SessionUtil)
    }

    @Test
    fun `when start activity should return payments from api`() {
        arrange {
            mockGetPaymentsList(paymentsUseCase)
        }
        act {
            fetchStatement(subject, observer)
        }
        assert {
            isStateSucess(subject)
        }
    }

    @Test
    fun `when user logout should clear their data`() {
        act {
            clearData(subject)
        }
        assert {
            isDataCleared()
        }

    }

}