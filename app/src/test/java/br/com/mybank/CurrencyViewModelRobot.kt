package br.com.mybank

import androidx.lifecycle.Observer
import br.com.mybank.data.SessionUtil
import br.com.mybank.data.StateResponse
import br.com.mybank.data.StateSuccess
import br.com.mybank.data.model.BackendError
import br.com.mybank.data.model.Statement
import br.com.mybank.domain.PaymentsResponseBO
import br.com.mybank.domain.usecase.PaymentsUseCase
import br.com.mybank.ui.dashboard.CurrencyViewModel
import io.mockk.every
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat


object CurrencyViewModelRobot {

    class CurrencyViewModelRobotArrange {
        private fun mockStatementList(): List<Statement> {
            return listOf(
                Statement(
                    date = "01/02/2020",
                    desc = "Pagamento",
                    title = "Água",
                    value = 180.0
                )
            )
        }

        fun mockGetPaymentsList(
            paymentsUseCase: PaymentsUseCase
        ) {
            every {
                paymentsUseCase.execute(any())
            } returns Single.just(
                PaymentsResponseBO(
                    backendError = BackendError(null, null),
                    statementList = mockStatementList()
                )
            )
        }
    }

    class CurrencyViewModelRobotAct {
        fun fetchStatement(
            subject: CurrencyViewModel,
            observer: Observer<StateResponse<PaymentsResponseBO>>
        ) {
            subject.paymentsResponse.observeForever(observer)
            subject.getPaymentsList()
            subject.paymentsResponse.removeObserver(observer)
        }

        fun clearData(
            subject: CurrencyViewModel
        ) {
            subject.clearUserData()
        }
    }

    class CurrencyViewModelRobotAssert {
        fun isStateSucess(subject: CurrencyViewModel) {
            subject.paymentsResponse.value.assertInstanceOf(StateSuccess::class.java)
        }

        fun isDataCleared() {
            assertEquals(SessionUtil.client, SessionUtil.client)
        }
    }

    fun arrange(func: CurrencyViewModelRobotArrange.() -> Unit) =
        CurrencyViewModelRobotArrange().apply(func)

    fun act(func: CurrencyViewModelRobotAct.() -> Unit) =
        CurrencyViewModelRobotAct().apply(func)

    fun assert(func: CurrencyViewModelRobotAssert.() -> Unit) =
        CurrencyViewModelRobotAssert().apply(func)

    infix fun <T> T.assertInstanceOf(clazz: Class<*>) {
        assertThat(this, instanceOf(clazz))
    }
}